package br.ueg.openodonto.util;

import java.util.Comparator;

import br.ueg.openodonto.dominio.OdontogramaDente;

public class OdontogramaDenteComparator implements Comparator<OdontogramaDente> {

	@Override
	public int compare(OdontogramaDente o1, OdontogramaDente o2) {
		int compareDente = o1.getDente().compareTo(o2.getDente());
		int compareFace = o1.getFace().compareTo(o2.getFace());
		int compare;
		if(compareDente == 0 && compareFace == 0){
			compare = 0;
		}else if(compareDente == 0){
			compare = compareFace;
		}else{
			compare = compareDente;
		}
		return compare;
	}

}
