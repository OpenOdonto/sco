package br.ueg.openodonto.util;


/**
 * @author vinicius.rodrigues
 * 
 */
public class WordFormatter {

	static String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
	static String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
	static char[] tabela;
	static {
		tabela = new char[256];
		for (int i = 0; i < tabela.length; ++i) {
			tabela[i] = (char) i;
		}
		for (int i = 0; i < acentuado.length(); ++i) {
			tabela[acentuado.charAt(i)] = semAcento.charAt(i);
		}
	}

	public static String remover(final String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); ++i) {
			char ch = s.charAt(i);
			if (ch < 256) {
				sb.append(tabela[ch]);
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String abstractStr(String str,int len){
		if(str == null || str.isEmpty()){
			return null;
		}
		return str.length() < len ? str : str.subSequence(0, len) + "...";
	}
	
	public static String formatarNome(String in) {
		int indexFimPrimeiroNome = in.indexOf(" ");
		if (indexFimPrimeiroNome == -1)
			return in;
		String nome = in.substring(0, indexFimPrimeiroNome).trim();
		String sobreNome = in.replace(nome, "").trim();
		String[] sobreNomes = sobreNome.split(" ");
		for (String sg : sobreNomes)
			nome += " " + sg.substring(0, 1).toUpperCase().trim();
		return nome;
	}
	
	public static String ocult(String plain){
		return plain.trim().replaceAll(".*", "*");
	}
	
	public static String formatErrorMessage(String label,String value,String message){
		return "* " + label +
		" = '" +
		abstractStr(value, 10) +
		"' : " +
		message;
	}

	public static String clear(String s) {
		if(s != null){
			s = s.replaceAll("[\\s{2}&&[^2]]", " ");
			s = s.replaceAll("[\\W&&[^ ]]", "");
			s = s.trim();
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(clear(" ").length());
	}
	
}