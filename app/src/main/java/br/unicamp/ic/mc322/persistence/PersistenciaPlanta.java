package br.unicamp.ic.mc322.persistence;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import br.unicamp.ic.mc322.exception.ArquivoCorrompidoException;
import br.unicamp.ic.mc322.exception.ArquivoInexistenteException;
import br.unicamp.ic.mc322.exception.PersistenciaException;
import br.unicamp.ic.mc322.service.GerenciadorProducao;

public class PersistenciaPlanta {
    private final Path arquivo;
    private final ConversorPlantaXml conversor = new ConversorPlantaXml;

    public PersistenciaPlanta(Path arquivo){
        if (arquivo == null){
            throw new IllegalArgumentException("O caminho do arquivo de persistencia nao pode ser nulo");
        }
        this.arquivo = arquivo;
    }

    /**
     * @return retorna o arquivo
     */
    public Path getArquivo(){
        return arquivo;
    }

    public boolean existeArquivo(){
        return Files.isRegularFile(arquivo);
    }

    public void salvar(GerenciadorProducao planta){
        if (planta == null){
            throw new IllegalArgumentException("Nao ha fabrica para salvar");
        }
        byte[] conteudo = gerarXml(planta);
        Path temporario = null;
        try {
            Path destino = arquivo.toAbsolutePath();
            Path pasta = destino.getParent();
            Files.createDirectories(pasta);
            temporario = Files.createTempFile(pasta, destino.getFileName().toString() + "-", ".tmp");
            Files.write(temporario, conteudo);
            substituir(temporario, destino);
            temporario = null;
        } catch (IOException e){
            throw new PersistenciaException("Nao foi possivel gravar  arquivo '" + arquivo + "': " + e.getMessage(), e);
        } finally {
            if (temporario != null){
                try{
                    Files.deleteIfExists(temporario);
                } catch (IOException ignorada){}
            }
        }
    }

    public GerenciadorProducao carregar(){
        byte[] bytes;
        try{
            bytes = Files.readAllBytes(arquivo);
        } catch (NoSuchFileException e){
            throw new ArquivoInexistenteException("O arquivo '" + arquivo + "' nao existe");
        } catch (IOException e){
            throw new PersistenciaException("Nao foi possivel ler o arquivo '" + arquivo + "': " + e.getMessage(), e);
        }
        if (new String (bytes, StandardCharsets.UTF_8).isBlank()){
            throw new ArquivoCorrompidoException("O arquivo '" + arquivo + "' esta vazio");
        }
        return conversor.ler(analisar(bytes));
    }

    private void substituir(Path temporario, Path destino) throws IOException{
        try{
            Files.move(temporario, destino, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException e){
            Files.move(temporario, destino, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private byte[] gerarXml(GerenciadorProducao planta){
        try{
            Document documento = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            conversor.escrever(planta, documento);

            TransformerFactory fabrica = TransformerFactory.newInstance();
            fabrica.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING,true);
            Transformer transformador = fabrica.newTransformer();
            transformador.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformador.setOutputProperty(OutputKeys.INDENT,"yes");
            transformador.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            transformador.transform(new DOMSource(documento), new StreamResult(saida));
            return saida.toByteArray();
        }catch (ParserConfigurationException | TransformerException e){
            throw new PersistenciaException("Falha ao gerar o XML da fabrica: " + e.getMessage(), e);
        }
    }

    private Document analisar(byte[] bytes){
        try{
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            fabrica.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            fabrica.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            fabrica.setXIncludeAware(false);
            fabrica.setExpandEntityReferences(false);

            DocumentBuilder construtor = fabrica.newDocumentBuilder();
            construtor.setErrorHandler(new ErrorHandler() {
                @Override public void warning(SAXParseException e){ }
                @Override public void error(SAXParseException e) throws SAXException{throw e;}
                @Override public void fatalError(SAXException e) throws SAXException{throw e;}
            });
            return construtor.parse(new ByteArrayInputStream(bytes));
        } catch (SAXException | IOException e){
            throw new ArquivoCorrompidoException("O arquivo '" + arquivo + "' nao eh um XML valido: " + e.getMessage(), e);
        } catch (ParserConfigurationException e){
            throw new PersistenciaException("Falha ao configurar a leitura de XML: " + e.getMessage(), e);
        }
    }
}
