package br.ueg.openodonto.dominio.constante;

public enum Dente {
	
	DENTE_18(18,"Terceiro Molar"),	DENTE_17(17,"Segundo Molar"),	DENTE_16(16,"Primeiro Molar"),	DENTE_15(15,"Segundo Pré-Molar"),	DENTE_14(14,"Primeiro Pré-Molar"),	DENTE_13(13,"Canino"),	DENTE_12(12,"Incisivo Lateral"),	DENTE_11(11,"Incisivo Central"),
	DENTE_21(21,"Incisivo Central"),	DENTE_22(22,"Incisivo Lateral"),	DENTE_23(23,"Canino"),	DENTE_24(24,"Primeiro Pré-Molar"),	DENTE_25(25,"Segundo Pré-Molar"),	DENTE_26(26,"Primeiro Molar"),	DENTE_27(27,"Segundo Molar"),	DENTE_28(28,"Terceiro Molar"),
	
	DENTE_48(48,"Terceiro Molar"),	DENTE_47(47,"Segundo Molar"),	DENTE_46(46,"Primeiro Molar"),	DENTE_45(45,"Segundo Pré-Molar"),	DENTE_44(44,"Primeiro Pré-Molar"),	DENTE_43(43,"Canino"),	DENTE_42(42,"Incisivo Lateral"),	DENTE_41(41,"Incisivo Central"),
	DENTE_31(31,"Incisivo Central"),	DENTE_32(32,"Incisivo Lateral"),	DENTE_33(33,"Canino"),	DENTE_34(34,"Primeiro Pré-Molar"),	DENTE_35(35,"Segundo Pré-Molar"),	DENTE_36(36,"Primeiro Molar"),	DENTE_37(37,"Segundo Molar"),	DENTE_38(38,"Terceiro Molar");
		

	private Integer     		numero;
	private String      		nome;
	private boolean             superior;
	private boolean             inferior;
	private boolean             left;
	private boolean             right;
	
	
	private Dente(Integer numero,String nome) {
		this.nome = nome;
		this.numero = numero;
		this.left = computeSide() == 0;
		this.right = computeSide() == 1;
		this.superior = computeHem() == 0;
		this.inferior = computeHem() == 1;
	}


	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	private int computeSide(){
		if(numero < 18 && numero > 11){
			return 0;
		}else if(numero > 21 && numero < 28){
			return 1;
		}else{
			return -1;
		}
	}

	private int computeHem(){
		if(numero > 10 && numero < 30){
			return 0;
		}else if(numero > 30 && numero < 50){
			return 1;
		}else{
			return -1;
		}
	}
	
	public static Dente getDente(Integer numero){
	    switch (numero) {
        case 11:
            return DENTE_11;
        case 12:
            return DENTE_12;
        case 13:
            return DENTE_13;
        case 14:
            return DENTE_14;
        case 15:
            return DENTE_15;
        case 16:
            return DENTE_16;
        case 17:
            return DENTE_17;
        case 18:
            return DENTE_18;
        case 21:
            return DENTE_21;
        case 22:
            return DENTE_22;
        case 23:
            return DENTE_23;
        case 24:
            return DENTE_24;
        case 25:
            return DENTE_25;
        case 26:
            return DENTE_26;
        case 27:
            return DENTE_27;
        case 28:
            return DENTE_28;
        case 31:
            return DENTE_31;
        case 32:
            return DENTE_32;
        case 33:
            return DENTE_33;
        case 34:
            return DENTE_34;
        case 35:
            return DENTE_35;
        case 36:
            return DENTE_36;
        case 37:
            return DENTE_37;
        case 38:
            return DENTE_38;
        case 41:
            return DENTE_41;
        case 42:
            return DENTE_42;
        case 43:
            return DENTE_43;
        case 44:
            return DENTE_44;
        case 45:
            return DENTE_45;
        case 46:
            return DENTE_46;
        case 47:
            return DENTE_47;
        case 48:
            return DENTE_48;
        default:
            return null;
        }
	}
	
	
	@Override
	public String toString() {
		return "Dente [nome=" + nome + ", numero=" + numero	+ "]";
	}


	public boolean isSuperior() {
		return superior;
	}

	public boolean isInferior() {
		return inferior;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

}
