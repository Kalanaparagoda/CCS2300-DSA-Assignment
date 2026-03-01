package module1;

import java.util.ArrayList;
import java.util.List;

public class LocationTree {

    static class Node {
        int id;
        String name;
        Node left, right;

        Node(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private Node root;

    public boolean insert(int id, String name) {
        if (searchById(id) != null) return false;
        root = insertRec(root, id, name);
        return true;
    }

    private Node insertRec(Node cur, int id, String name) {
        if (cur == null) return new Node(id, name);
        if (id < cur.id) cur.left = insertRec(cur.left, id, name);
        else cur.right = insertRec(cur.right, id, name);
        return cur;
    }

    public boolean delete(int id) {
        if (searchById(id) == null) return false;
        root = deleteRec(root, id);
        return true;
    }

    private Node deleteRec(Node cur, int id) {
        if (cur == null) return null;

        if (id < cur.id) cur.left = deleteRec(cur.left, id);
        else if (id > cur.id) cur.right = deleteRec(cur.right, id);
        else {
            // found
            if (cur.left == null) return cur.right;
            if (cur.right == null) return cur.left;

            Node succ = minNode(cur.right);
            cur.id = succ.id;
            cur.name = succ.name;
            cur.right = deleteRec(cur.right, succ.id);
        }
        return cur;
    }

    private Node minNode(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    public Node searchById(int id) {
        Node cur = root;
        while (cur != null) {
            if (id == cur.id) return cur;
            cur = (id < cur.id) ? cur.left : cur.right;
        }
        return null;
    }

    public List<Node> inOrder() {
        List<Node> out = new ArrayList<>();
        inOrderRec(root, out);
        return out;
    }

    private void inOrderRec(Node cur, List<Node> out) {
        if (cur == null) return;
        inOrderRec(cur.left, out);
        out.add(cur);
        inOrderRec(cur.right, out);
    }

    public boolean isEmpty() {
        return root == null;
    }
}
