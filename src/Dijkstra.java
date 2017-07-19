import java.util.*;

/**
 * Created by michaelfleischmann on 7/14/17.
 */

class Node implements Comparable<Node> {

    private final String vertexName;
    private final double x;
    private final double y;
    private final double z;
    private double distanceFromOrigin;
    private boolean done;

    /**
     * A node can be thought of as any vertex in the Graph
     *
     * @param vertexName the name given to the vertex
     * @param x the x coordinate of the vertex in the 3D space
     * @param y the y coordinate of the vertex in the 3D space
     * @param z the z coordinate of the vertex in the 3D space
     */

    Node(String vertexName, double x, double y, double z) {
        this.vertexName = vertexName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.distanceFromOrigin = Double.POSITIVE_INFINITY;
        this.done = false;
    }

    @Override
    public int compareTo(Node n) {
        return Double.compare(this.distanceFromOrigin, n.distanceFromOrigin);
    }

    @Override
    public String toString() {
        return this.vertexName + ":" + this.distanceFromOrigin;
    }

    /**
     * Tests if the distance from a prev node to this one is less than 3, and if so, adds the distance from the origin
     * @param prev the previous node
     * @return null if distance is more than 3, otherwise this distance + previous node's distance
     */
    Double distance(Node prev) {
        double distance = Math.sqrt(Math.pow((prev.x - this.x), 2) + Math.pow((prev.y - this.y), 2) +
                Math.pow((prev.z - this.z), 2));
        return distance > 3.0 ? null : distance + prev.distanceFromOrigin;
    }

    double getDistanceFromOrigin() {
        return this.distanceFromOrigin;
    }


    void setDistance(Double d) {
        this.distanceFromOrigin = d;

    }

    void done() {
        this.done = true;
    }

    boolean isDone() {
        return done;
    }
}

class Graph {
    private HashMap<Node, ArrayList<Node>> adjacencies;

    /**
     * A Graph of nodes that are given as an arraylist
     * @param nodes an arraylist of all nodes in the Graph
     */
    Graph(ArrayList<Node> nodes) {
        if(nodes.isEmpty()) {
            throw(new IllegalArgumentException("Empty Node List"));
        }
        nodes.get(0).setDistance(0.0);
        this.adjacencies = new HashMap<>();
        for(Node v : nodes) {
            this.adjacencies.put(v, new ArrayList<>());
            for(Node n : nodes) {
                if(n.distance(v) != null && v != n) {
                    this.adjacencies.get(v).add(n);
                }
            }
        }

    }

    void dijkstra(Node s) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(s);
        while(!queue.isEmpty()) {
            Node v = queue.poll();
            v.done();
            if(v.getDistanceFromOrigin() != 0) {
                System.out.println(v);
            }
            for(Node n : this.adjacencies.get(v)) {
                Double d = n.distance(v);
                if (!queue.contains(n) && !n.isDone()) {
                    n.setDistance(d);
                    queue.offer(n);
                }
                else if(d < n.getDistanceFromOrigin()) {
                    n.setDistance(d);
                }
            }
        }
    }
}

class Dijkstra {
    public static void main(String[] args) {
        ArrayList<Node> nodes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("[\n,]");
        while(scanner.hasNext()) {
            String s = scanner.next();
            Double x = scanner.nextDouble();
            Double y = scanner.nextDouble();
            Double z = scanner.nextDouble();
            nodes.add(new Node(s, x, y, z));
        }
        Graph G = new Graph(nodes);
        G.dijkstra(nodes.get(0));
    }

}