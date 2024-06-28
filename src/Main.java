import models.Business;
import models.HashTable;
import models.Entry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String line = "";
        String splitBy = ",";
        int hashFunction1 = 1; // Hash utilizando suma
        int hashFunction2 = 2; // Hash utilizando división


        HashTable<String, Business> hashTable1 = new HashTable<>(16, hashFunction1);

        HashTable<String, Business> hashTable2 = new HashTable<>(16, hashFunction2);


        LinkedList<String> addedBusinesses = new LinkedList<>();

        LinkedList<String> newlyAddedBusinesses = new LinkedList<>();

        String filePath = "C:\\Users\\yara2\\IdeaProjects\\dataset\\bussines.csv";


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] businessData = line.split(splitBy);
                String idStr = businessData[0];
                String name = businessData[1];
                String address = businessData[2];
                String city = businessData[3];
                String state = businessData[4];

                Business business = new Business(idStr, name, address, city, state);


                hashTable1.put(idStr, business);
                hashTable2.put(idStr, business);

                addedBusinesses.add(idStr);

                System.out.println("Business [ID=" + idStr + ", Name=" + name + ", Address=" + address + ", City=" + city + ", State= " + state + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 4) {
            System.out.println("\nMenu:");
            System.out.println("1. Agregar nuevo negocio");
            System.out.println("2. Buscar negocio por ID");
            System.out.println("3. Imprimir todos los negocios agregados durante la ejecución");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:

                    System.out.print("Ingrese el ID del nuevo negocio: ");
                    scanner.nextLine();
                    String newId = scanner.nextLine();
                    System.out.print("Ingrese el nombre del nuevo negocio: ");
                    String newName = scanner.nextLine();
                    System.out.print("Ingrese la dirección del nuevo negocio: ");
                    String newAddress = scanner.nextLine();
                    System.out.print("Ingrese la ciudad del nuevo negocio: ");
                    String newCity = scanner.nextLine();
                    System.out.print("Ingrese el estado del nuevo negocio: ");
                    String newState = scanner.nextLine();

                    Business newBusiness = new Business(newId, newName, newAddress, newCity, newState);

                    hashTable1.put(newId, newBusiness);
                    hashTable2.put(newId, newBusiness);


                    newlyAddedBusinesses.add(newId);

                    System.out.println("Negocio agregado correctamente.");
                    break;
                case 2:
                    // Buscar negocio por ID
                    System.out.print("Ingrese el ID del negocio a buscar: ");
                    scanner.nextLine(); // Consumir el salto de línea pendiente
                    String searchId = scanner.nextLine();

                    long startTime1 = System.nanoTime();
                    Business result1 = hashTable1.get(searchId);
                    long endTime1 = System.nanoTime();
                    long duration1 = endTime1 - startTime1;

                    long startTime2 = System.nanoTime();
                    Business result2 = hashTable2.get(searchId);
                    long endTime2 = System.nanoTime();
                    long duration2 = endTime2 - startTime2;

                    if (result1 != null) {
                        System.out.println("Negocio encontrado en hashTable1: " + result1);
                        System.out.println("Tiempo de búsqueda usando hash1: " + duration1 + " ns");
                    } else {
                        System.out.println("Negocio con ID '" + searchId + "' no encontrado en hashTable1.");
                    }

                    if (result2 != null) {
                        System.out.println("Negocio encontrado en hashTable2: " + result2);
                        System.out.println("Tiempo de búsqueda usando hash2: " + duration2 + " ns");
                    } else {
                        System.out.println("Negocio con ID '" + searchId + "' no encontrado en hashTable2.");
                    }
                    break;
                case 3:

                    System.out.println("\nNegocios agregados durante la ejecución:");

                    for (String id : newlyAddedBusinesses) {
                        Business business1 = hashTable1.get(id);
                        Business business2 = hashTable2.get(id);
                        if (business1 != null) {
                            System.out.println("En hashTable1: " + business1);
                        }
                        if (business2 != null) {
                            System.out.println("En hashTable2: " + business2);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
        scanner.close();
    }
}
