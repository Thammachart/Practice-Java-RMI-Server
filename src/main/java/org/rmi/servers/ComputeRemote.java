package org.rmi.servers;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.rmi.components.ComputeRemoteInterface;
import org.rmi.components.Task;

public class ComputeRemote implements ComputeRemoteInterface {

    @Override
    public <T> T executeTask(Task<T> task) {
        return task.execute();
    }

    public static void main(String args[]) {
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            String name = "Compute";
            ComputeRemoteInterface remote = new ComputeRemote();
            ComputeRemoteInterface stub = (ComputeRemoteInterface) UnicastRemoteObject.exportObject(remote, 0);

            System.out.println("Locating Registry...");
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ComputeRemote bound");
        } catch(Exception e) {
            System.err.println("Exception: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
