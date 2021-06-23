package code;

import java.util.ArrayList;

public class Chien {
	
	String nom;
	String race;
	int age;
	
	public Chien(String nom, String race) {
		this.race = race;
		this.nom = nom;
		
		int ajoute = 0;
		for(int i =0; i < 10; i++) {
			ajoute = ajoute + i;
		}
		
		ajoute = 0;
		while(ajoute < 50) {
			ajoute += 2;
		}
		
		
	}
	
	public void dormir() {
		System.out.println("Le chien dort");
	}
	
	public void jouer() {
		System.out.println("Le chien joue");
	}
	
	public int calculer_année_naissance() {
		return 2021- this.age;
	}
	
	public boolean est_plus_vieux(int age_autre_chien) {
		if(this.age < age_autre_chien) {
			return true;
		}else {
			return false;
		}
	}
	
	public void parcourir() {
		ArrayList<String> ma_liste = new ArrayList<>();
		ma_liste.add("Test");
		ma_liste.add("Jambon");
		ma_liste.add("AAAAAA");
		ma_liste.add("mamamia");
		ma_liste.add("nICE");
		ma_liste.add("cc");
		
		for( int i =0; i < ma_liste.size(); i++) {
			System.out.println(ma_liste.get(i));
		}
		
	}
	
	
	
}