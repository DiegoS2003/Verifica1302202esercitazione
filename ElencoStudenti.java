public class ElencoStudenti {
    private Studente[] elenco=new Studente[100];
    public void svuota(){
        dimElenco=0;
    }
    public Studente[] getElenco() {
        return elenco;
    }

    public void setElenco(Studente[] elenco) {
        this.elenco = elenco;
    }

    public int getDimElenco() {
        return dimElenco;
    }

    public void setDimElenco(int dimElenco) {
        this.dimElenco = dimElenco;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    private int dimElenco=0;
    private int max=100;// ATTENZIONE RICORDATEVI CHE I VETTORI DI OGGETTI COME IN C NON CONSERVANO QUANTI ELEMENTI CI ABBIAMO MESSO QUINDI USATE UN CONTATORE INT O NullPointerException ucciderà la vostra famiglia
    public ElencoStudenti(){
        //non serve niente, basta lasciarlo vuoto
    }
    public Studente[] aggiungiStudente(Studente a) throws ElencoPienoException {
        if(dimElenco+1>max){
            throw new ElencoPienoException();
        }
        elenco[dimElenco]=a;
        dimElenco++;
        return elenco;
    }
    public Studente BuildStudente(String nome,String cognome){
        Studente s=new Studente(nome,cognome);
        return s;// creiamo uno studente da poi usare per aggiungerlo alla lista
    }
    public Studente[] rimuoviStudente(Studente a) throws StudenteNonTrovato {
        for(int i=0;i<dimElenco;i++){
            if(elenco[i].getNome().equals(a.getNome())&&elenco[i].getCognome().equals(a.getCognome())){
                elenco[i]=elenco[dimElenco-1];// spostiamo l'ultimo elemento della lista al posto dell'elemento della tabella che vogliano eliminare dalla lista e poi diciamo che la lista è più corta, così  l'ultimo elemento  non verrà letto da updateTabella e dalla ricerca e sarà sovrascritto all'inserimento di un nuovo utente
                dimElenco--;
                return elenco;
            }
        }

        throw new StudenteNonTrovato();
    }

}
