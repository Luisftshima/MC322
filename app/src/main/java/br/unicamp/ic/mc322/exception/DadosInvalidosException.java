package br.unicamp.ic.mc322.exception;

/**
 * 
 * DadosInvalidosException
 * Tratamento de erro para estrutura valida, mas valores que violam as regras da fabrica
 */
public class DadosInvalidosException extends PersistenciaException{
    public DadosInvalidosException(String mensagem){
        super(mensagem);
    }
    public DadosInvalidosException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
