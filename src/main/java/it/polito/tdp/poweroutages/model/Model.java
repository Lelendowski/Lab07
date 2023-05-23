package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.*;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	List<PowerOutages> parziale = new LinkedList<PowerOutages>();
	List<PowerOutages> soluzioneOttimale = new LinkedList<PowerOutages>();
	int numeroPersoneCoinvolte = 0;
	
	PowerOutageDAO podao;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	
	public String chiamaRicorsione(Nerc nerc, int ore, int anni) {
		LinkedList<PowerOutages> listaDB_PowerOutages =
				new LinkedList<PowerOutages>(podao.getPowerOuteagesOfNercList(nerc.getId()));
		LinkedList<PowerOutages> parziale = new LinkedList<PowerOutages>(); 
		ricorsione(listaDB_PowerOutages, parziale, ore, anni);
		
		
		String sol = "";
		for(PowerOutages p: this.soluzioneOttimale) {
			sol = sol + p.getPowerOutagesId() +", ";
		}
		return "Totale di " + numeroPersoneCoinvolte + " persone coinvolte."; 
	}
	
	void ricorsione(LinkedList<PowerOutages> listaTotalePowerOuteges, LinkedList<PowerOutages> parziale, int maxOre, int maxAnni) {
		
		boolean ore = controlloOre(maxOre, parziale); 
		boolean anni = controlloAnni(parziale, maxAnni);
		
		if(ore && anni) {
			int peopleAffected =0;
			
			for(PowerOutages pj: parziale) {
				peopleAffected = peopleAffected + pj.getCustomers_affected();
			}
			
			if(peopleAffected>numeroPersoneCoinvolte) {
				numeroPersoneCoinvolte = peopleAffected;
				soluzioneOttimale= parziale;
			}
			for(PowerOutages pi: listaTotalePowerOuteges) {
				if(!parziale.contains(pi)) {
					parziale.add(pi);  
				 		ricorsione(listaTotalePowerOuteges,
				 				parziale, maxOre, maxAnni);
				 		parziale.remove(pi);
				}
			}
		}else {
			return; 
			}
	}
		
		
		
		
		//		for (int i=0; i<listaTotalePowerOuteges.size(); i++) {
//			if (parziale.size()==0) {
//				for(PowerOutages pi: listaTotalePowerOuteges) {
//					parziale.add(pi);  
//			 		ricorsione(listaTotalePowerOuteges,
//			 				parziale, maxOre, maxAnni);
//			 		parziale.remove(0);
//				}
//			}else {
//				for(PowerOutages pi: listaTotalePowerOuteges) {
//					boolean ore = controlloOre(maxOre, parziale); 
//					boolean anni = controlloAnni(parziale, maxAnni);
//					
//					if(ore && anni) {
//						int peopleAffected =0;
//						for(PowerOutages pj: parziale) {
//							numeroPersoneCoinvolte = peopleAffected +
//									pj.getCustomers_affected();
//						}
//						if(peopleAffected>numeroPersoneCoinvolte) {
//							numeroPersoneCoinvolte = peopleAffected;
//							//Azzero il contatore per il prossimo giro
//							peopleAffected=0;
//						}
//						
//						
//				}else {
//					break; 
//					}
//				}
//			}
//		}
	
	
	public boolean controlloOre (int maxOre, LinkedList<PowerOutages> lista) {
		
		if (lista.isEmpty())
			return true;
		
		long sommaOre = 0;
		for(PowerOutages p: lista) {
			sommaOre = sommaOre +
					(p.getDate_event_began().until(p.getDate_event_finished(), ChronoUnit.HOURS));
		}
		if(sommaOre <= maxOre) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean controlloAnni (LinkedList<PowerOutages> lista,
			int differenzaMaxAnni){
		if (lista.isEmpty() || ((lista.get(lista.size()-1).getDate_event_finished().until(lista.get(0).getDate_event_began(),
				ChronoUnit.YEARS)) < differenzaMaxAnni)) { 
			return true;
		}
		else {
			return false;
		}
	}	
	

}



























