package epsilonserver.entity;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Test map containing a list of players
 * @author mm
 */
public class EntityHandler {

    private ConcurrentHashMap<InetAddress, NetworkEntity> entityList;
    //Map<InetAddress, NetworkEntity> entityList;

    /**
     * Private constructor
     * Initialize hashmap containing players
     */
    private EntityHandler() {
        entityList = new ConcurrentHashMap<InetAddress, NetworkEntity>();
        //entityList = Collections.synchronizedMap(new HashMap<InetAddress, NetworkEntity>());

//        String[] s1 = new String[2];
//        s1[0] = "-70";
//        s1[1] = "200";
//
//        String[] s2 = new String[2];
//        s2[0] = "-80";
//        s2[1] = "300";
//
//        InetAddress ip1 = null;
//        InetAddress ip2 = null;
//
//        try {
//            ip1 = InetAddress.getByName("158.38.141.106");
//            ip2 = InetAddress.getByName("158.38.141.110");
//        }
//        catch (UnknownHostException e) {
//
//        }
//
//        NetworkEntity n1 = new NetworkEntity("jonas", ip1, s1);
//        NetworkEntity n2 = new NetworkEntity("bob", ip2, s2);
//        entityList.put(ip1, n1);
//        entityList.put(ip2, n2);
    }

    /**
     * Inner class to create a instance of EntityHandler, which is
     * loaded when EntityHandler.getInstance() method is called or
     * when INSTANCE is accessed.
     */
    private static class EntityHandlerHolder {
        public static final EntityHandler INSTANCE = new EntityHandler();
    }

    /**
     * Get instance of EntityHandler
     * @return INSTANCE
     */
    public static EntityHandler getInstance() {
        return EntityHandlerHolder.INSTANCE;
    }

//    /**
//     * Add a player to the playerlist
//     * @param name
//     * @param p
//     */
//    public void addPlayer(String name, InetAddress ip, String[] posArray) {
//        NetworkEntity entity = new NetworkEntity(name, ip, posArray);
//        entityList.put(ip, entity);
//    }

//    /**
//     *
//     * @param ip
//     * @param posArray
//     */
//    public void setPlayerCoordinates(InetAddress ip, String[] posArray) {
//        NetworkEntity entity = entityList.get(ip);
//        entity.setCoordinates(posArray);
//    }

    /**
     *
     * @param ip
     * @return
     */
    public String getGameStateString(InetAddress ip) {

        //synchronized (entityList) {
        String gs = "";

        Collection c = entityList.values();
        Iterator it = c.iterator();

        
            while (it.hasNext()) {
                NetworkEntity n = (NetworkEntity) it.next();
                if (!n.getAddress().equals(ip)) {
                    gs += n.getPlayerState();
                }
            }
            
            return gs;
        //}
    }

    /**
     *
     * @return
     */
    public InetAddress[] getAddressArray() {
        //synchronized (entityList) {
        InetAddress[] adrArray = new InetAddress[entityList.size()];
        adrArray = (InetAddress[]) entityList.keySet().toArray(adrArray);
        return adrArray;
        //}
    }

//    /**
//     * Check if player exists
//     *
//     * @param ip
//     * @return
//     */
//    public boolean hasPlayer(InetAddress ip) {
//        //synchronized (entityList) {
//            boolean hasPlayer = false;
//            if (entityList.containsKey(ip)) {
//                hasPlayer = true;
//            }
//            return hasPlayer;
//        //}
//    }

    public void createIfAbsent(InetAddress ip, String name, String[] posArray) {

        //synchronized (entityList) {
        boolean contains = entityList.containsKey(ip);
        if (contains) {
            entityList.get(ip).setCoordinates(posArray);
        }
        else {
            NetworkEntity n = new NetworkEntity(name, ip, posArray);
            entityList.put(ip, n);
        }
        //}

    }

}