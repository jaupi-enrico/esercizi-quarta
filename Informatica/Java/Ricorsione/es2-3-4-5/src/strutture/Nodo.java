package strutture;

import java.util.LinkedList;

class Nodo {
    int info;
    LinkedList<Nodo> figli;

    Nodo(int info){
        this.info = info;
        figli = new LinkedList<>();
    }
}