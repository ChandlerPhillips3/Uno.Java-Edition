// Reference: https://www.softwaretestinghelp.com/doubly-linked-list-in-java/#:~:text=A%20circular%20doubly%20linked%20list,pointers%20are%20set%20to%20null.
class Node{
    Players player;
    Node next;
    Node prev;

    Node(Players player){
        this.player=player;
        next=null;
        prev=null;
    }
}

public class AllPlayers {
    Node head;
    int size;

    public AllPlayers() {
        head = null;
    }

    public void addPlayer(Players player) {
        Node newPlayer = new Node(player);
        if (head == null) {
            head = newPlayer;
            head.prev = head;
            head.next = head;
        } else {
            Node temp = head;
            temp.next = newPlayer;
            newPlayer.prev = temp;
            newPlayer.next = head;
            head.prev = newPlayer;
        }
        size++;
    }

    public Node skipPlayer(Node currentPlayer) {
        Node temp = currentPlayer;
        temp = temp.next.next;
        return temp;
    }

    public Node nextPlayer(Node currentPlayer) {
        Node temp = currentPlayer;
        temp = temp.next;
        return temp;
    }

    // https://www.geeksforgeeks.org/reverse-a-doubly-linked-list/
    public void reverse() {
        // if no players
        if( head == null) {
            return;
        }

        Node current = head;
        Node temp = null;

        // swapping next and prev players.
        while(current != head || current.next != null) {
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.prev;
        }

        if(temp != null) {
            head = head.prev;
        }
    }

    // public void distributeCards(int totalCards) {
    //     Node temp = head;
    //     for(int i = 0; i < size; i++) {
    //         for(int j = 0; j < totalCards; j++) {
    //             temp.player.hand.add(Activities.drawCard(temp.player));
    //         }
    //         temp = temp.next;
    //     }
    // }

    // used to draw cards if player uses draw 2 or draw 4
    // public void specialDraw(Node currentPlayer, int total) {
    //      Node temp = currentPlayer.next;
    //      for(int i = 0; i < total; i++) {
    //          temp.player.hand.add(Activities.drawCard(currentPlayer.player));
    //      }
    // }

}