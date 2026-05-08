package strutture;


/**
 * Nell'implementazione dei metodi si è considerato che sia sempre
 * presente il nodo root, se così non fosse verrebbero lanciate eccezioni
 * Un modo per risolverlo è eliminare il costruttore vuoto, in modo
 * da garantire che almeno il nodo root ci sia, oppure vanno implementati
 * i controlli nei metodi che lo richiedono
 */
public class Albero {
    int n_items;
    Nodo root;

    public Albero(int n){
        root = new Nodo(n);
        n_items = 1;
    }

    public Albero(){
        root = null;
        n_items = 0;
    }

    private Nodo cercaNodo(Nodo attuale, int cercato){
        if(attuale.info == cercato){
            return  attuale;
        }
        for (Nodo f : attuale.figli){
            Nodo temp = cercaNodo(f, cercato);
            if ( temp != null){
                return temp;
            }
        }
        return null;
    }

    public boolean add(int dove, int n){
        Nodo temp = cercaNodo(root, dove);
        if (temp != null){
            temp.figli.add(new Nodo(n));
            n_items++;
            return true;
        }
        return false;
    }

    private void visitaAnticipataR(Nodo attuale){
        System.out.print(attuale.info + " ");
        for (Nodo f : attuale.figli){
            visitaAnticipataR(f);
        }
    }

    public void visitaAnticipata(){
        visitaAnticipataR(root);
        System.out.println("");
    }

    private void visitaPosticipataR(Nodo attuale){
        for (Nodo f : attuale.figli){
            visitaPosticipataR(f);
        }
        System.out.print(attuale.info + " ");
    }

    public void visitaPosticipata(){
        visitaPosticipataR(root);
        System.out.println("");
    }

    private int altezzaR(Nodo n){
        int altezza = - 1;
        for (Nodo f : n.figli){
            if (altezzaR(f) > altezza){
                altezza = altezzaR(f);
            }
        }
        return altezza + 1;
    }

    public int altezza(){
        return altezzaR(root);
    }

    public int sizeR(Nodo n) {
        int size = 1;
        for (Nodo f : n.figli) {
            size += sizeR(f);
        }
        return size;
    }

    public int size() {
        return sizeR(root);
    }

    public int maxR(Nodo n) {
        int max = n.info;
        for (Nodo f : n.figli) {
            int temp = maxR(f);
            max = max < temp ? temp : max;
        }
        return max;
    }

    public int max() {
        return maxR(root);
    }

    public int profonditaR(Nodo n, int val) {
        if (n.info == val) {
            return 0;
        }
        for (Nodo f : n.figli) {
            int temp = profonditaR(f, val);
            if (temp != -1) {
                return temp + 1;
            }
        }
        return -1;
    }

    public int profondita(int valore) {
        return profonditaR(root, valore);
    }

    public boolean containsR(Nodo n, int val) {
        if (n.info == val) {
            return true;
        }
        for (Nodo f : n.figli) {
            if (containsR(f, val)) {
               return true;
            }
        }
        return false;
    }

    public boolean addUnico(int dove, int val) {
        if (containsR(root, val)) {
            return false;
        }
        Nodo temp = cercaNodo(root, dove);
        if (temp == null) {
            return false;
        }
        temp.figli.add(new Nodo(val));
        n_items++;
        return true;
    }
}