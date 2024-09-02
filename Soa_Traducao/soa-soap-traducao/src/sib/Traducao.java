package sib;

import javax.jws.WebService;
import sei.ITraducao;

@WebService(endpointInterface = "sei.ITraducao")
public class Traducao implements ITraducao {

    @Override
    public String traduzir(String palavra) {
       if (palavra.equalsIgnoreCase("casa")) {
        return "house";
       } else {
        return "Palavra não suportada!";
       
       }
    }

    
}
