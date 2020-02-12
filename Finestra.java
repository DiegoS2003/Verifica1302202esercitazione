import javax.print.attribute.SetOfIntegerSyntax;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Finestra extends JFrame implements ActionListener {
    //Definite qui gli oggetti dell'interfaccia e non in initComponents sennò  le altre funzioni, soprattutto actionPerformed non li vedono
    private JMenuBar bar;
    private JMenu studente;
    private JMenuItem svuota;
    private JButton delete,buttonAdd;
    private JLabel delTitle;
    private JTextField surname,name,nameAdd,surnameAdd;
    private JTable tabella;
    private JScrollPane pane;
    private GridBagConstraints c;
    private DefaultTableModel defTableModel;
    private ElencoStudenti elenco= new ElencoStudenti();
    public void initComponents(){

        this.setLayout(new GridBagLayout()); //usiamo un gridBagLayout
        c= new GridBagConstraints();// cra un constraint che verrà usato ogni volta
        bar=new JMenuBar();

        studente = new JMenu("Studenti");

        svuota= new JMenuItem("Svuota Elenco");

        surname=new JTextField(15);
        name=new JTextField(15);
        nameAdd=new JTextField(15);
        surnameAdd=new JTextField(15);

        buttonAdd=new JButton("Aggiungi");
        delete=new JButton("Elimina");

        delTitle= new JLabel("Elimina per nome e  cognome:");

        Object[][] data = {}; //così si crea una tabella inizialmente vuota (sarebbe un elenco di righe, che a loro volta sarebbero un elenco di celle, ossia {{cella,cella,della},{cella,cella,cella}}, dove cella è il valore della cella
        String[] columnNames = {"Nome","Cognome"};// elenco di colonne
        defTableModel = new DefaultTableModel(data,columnNames);// crea un modello tabella, ossia il contenuto della tabella. Ogni cambiamento effettuato si rifletterà sulla JTable presente nell'interfaccia
        tabella = new JTable(defTableModel);//crea Tabella usando il modello.
        // metti la tabella nello scrollpane: se la tabella diventa troppo grande si crea una barra di scorrumento
        svuota.addActionListener(this);
        studente.add(svuota);
        bar.add(studente);
        /*
        Allora... Seguitemi
        Pensate ad un GridBagLayout come una tabella: sopstandosi verso destra le x aumentano, spostandosi verso il basso le y aumentano
        per ogni elemento cambiamo la proprietà del constraint segnando la x e y della tabell+a (ricordate, un paio di coordinate per ogni oggetto) e poi impostando la lunghezza se vogliamo che occupi più celle
        (gridwidth per aumentare la lunghezza e gridheight per aumentare l'altezza)
        entrambe hanno 1 come predefinito
        una volta che abbiamo creato il constraint lo passiamo con add come secondo parametro e voilà
         */
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=4;
        this.add(bar,c);

        c.gridx=0;
        c.gridy=1;
        c.gridwidth=4;
        this.add(pane,c);

        c.gridx=0;
        c.gridy=2;
        c.gridwidth=1;
        this.add(delTitle, c);

        c.gridx=1;
        c.gridy=2;
        c.gridwidth=1;
        this.add(name, c);

        c.gridx=2;
        c.gridy=2;
        c.gridwidth=1;
        this.add(surname, c);

        c.gridx=3;
        c.gridy=2;
        c.gridwidth=1;
        this.add(delete, c);
        delete.addActionListener(this);
        c.gridx=0;
        c.gridy=3;
        c.gridwidth=1;
        this.add(surnameAdd, c);

        c.gridx=1;
        c.gridy=3;
        c.gridwidth=1;
        this.add(nameAdd, c);


        c.gridx=2;
        c.gridy=3;
        c.gridwidth=1;
        this.add(buttonAdd, c);
        buttonAdd.addActionListener(this);
    }
    public static void main(String[] args){
        Finestra x=new Finestra();
        x.setTitle("Gestisci Studenti");
        x.setSize(800,600);
        x.setVisible(true);

    }
    public void updateTabella(ElencoStudenti a){
        defTableModel.setRowCount(0);//Imposta il numero di righe a 0 eliminando tutte le righe presenti
        for(int i=0;i<a.getDimElenco();i++){
            Object[] row={a.getElenco()[i].getNome(),a.getElenco()[i].getCognome()};//Crea riga temporanea
            defTableModel.addRow(row);//aggiungi riga
        }


    }
    public Finestra(){
        initComponents();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(svuota)){
            defTableModel.setRowCount(0);//settando a 0 tutte le righe presenti vengono eliminate
            elenco.svuota(); // è un po' una porcata ma va bene lo stesso
        }
        else if(e.getSource().equals(delete)){
            Studente a =elenco.BuildStudente(name.getText(),surname.getText());
            try {
                elenco.rimuoviStudente(a);
                updateTabella(elenco);
            } catch (StudenteNonTrovato studenteNonTrovato) {
                //e niente non lo eliminiamo perchè non c'è lol
            }
        }
        else if(e.getSource().equals(buttonAdd)){
            Studente a =elenco.BuildStudente(nameAdd.getText(),surnameAdd.getText());
            try {
                elenco.aggiungiStudente(a);
                updateTabella(elenco);
            } catch (ElencoPienoException ex) {
                //non aggiungiamo niente perchè è già pieno e non abbiamo previsto un JLabel che dica che lo spazio è finito
            }
        }
    }


}
/*
Classe studente nome cognome
classe elencoStudenti  metodi ottieni elenco aggiungi studente e rimuovi studente per oggetto studente questo
interfaccia grafica con tabella studenti correnti, jtextfield jbuttons(rimuovi studente) by cognome ->
e poi aggiungi
nome cognome
jmenu bar con studeni -> aggiungi,svuota elenco
gridbaggedlayout





-jmenubar-
-jscrollpane(jtable)-
-jtextfield(cognome)-jbutton(rimuovi studente)-

 */
