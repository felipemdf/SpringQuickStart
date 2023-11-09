package com.felipemdf.SpringQuickStart.core.utils;

public class StringFormats {

    public static String removeNonDigits(String texto) {
        return texto==null ? "": texto.replaceAll("\\D", "");
    }
    public static String formatCpfCnpj(String cep) {
        String texto = removeNonDigits(cep);
        if(texto.length()==11)
            return texto.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        else if (texto.length()==14)
            return texto.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        else
            return texto;
    }
}
