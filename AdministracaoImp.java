/**
 * Implementação das funções assinadas na interface
 */

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;

//UnicastRemoteObject permite que a implementacao da classe possa ser estabelecida como um servico remoto
public class AdministracaoImp extends UnicastRemoteObject implements InterSolicitacoes {

    private Map<Integer, Double> contas;
    private int idConta;
    private List<String> bufferTransacoes;

    public AdministracaoImp() throws RemoteException{
        contas = new TreeMap<Integer,Double>();
        idConta = 0;
        bufferTransacoes = new ArrayList<String>();
    }

    @Override
    public String aberturaDeConta(String idTrancaocao) throws RemoteException {
        if(bufferTransacoes.contains(idTrancaocao)){
            return "\nConta já existe\n";
        }

        bufferTransacoes.add(idTrancaocao);
        contas.put(idConta++, 0.00);
        return "\nConta aberta\n";
    }

    @Override
    public String fechamentoDeConta(int conta) throws RemoteException {
        if(contas.containsKey(conta)){
            contas.remove(conta);
            return "\nConta removida\n";
        }else{
            return "\nConta não existe\n";
        }
        
    }

    @Override
    public String autenticaçãoDeConta(int conta) throws RemoteException {
        if(contas.containsKey(conta)){
            return "\nConta validada\n";
        }else{
            return "\nConta não existe\n";
        }
    }

    @Override
    public String deposito(int id, double valor, String idTrancaocao) throws RemoteException {
        if(bufferTransacoes.contains(idTrancaocao)){
            return "\nDeposito já foi realizado\n";
        }

        if(contas.containsKey(id)){
            bufferTransacoes.add(idTrancaocao);
            double novovalor = contas.get(id)+valor;
            contas.remove(id);
            contas.put(id, novovalor);
            return "\nDeposito realizado\n";
        }else{
            return "\nConta não existe\n";
        }
    }

    @Override
    public String retirada(int id, double valor, String idTrancaocao) throws RemoteException {
        if(bufferTransacoes.contains(idTrancaocao)){
            return "\nSaque já foi realizado\n";
        }
        if(contas.containsKey(id)){
            bufferTransacoes.add(idTrancaocao);
            double novovalor = contas.get(id)-valor;
            contas.remove(id);
            contas.put(id, novovalor);
            return "\nSaque realizado\n";
        }else{
            return "\nConta não existe\n";
        }
    }

    @Override
    public String consultaSaldo(int conta) throws RemoteException {
        if(contas.containsKey(conta)){
         return "\nSaldo: " + contas.get(conta) + "\n";
        }else{
            return "\nConta não existe\n";
        }
    }

}
