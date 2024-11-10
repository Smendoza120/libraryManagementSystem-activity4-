package com.mycompany.librarymanagement;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagement {

    static class LibraryNode {

        String id;
        String bookName;
        String author;
        LibraryNode left, right;

        public LibraryNode(String id, String bookName, String author) {
            this.id = id;
            this.bookName = bookName;
            this.author = author;
            this.left = this.right = null;
        }
    }

    static class UserNode {

        int cedula;
        String userName;
        String lastName;
        UserNode left, right;

        public UserNode(int cedula, String userName, String lastName) {
            this.cedula = cedula;
            this.userName = userName;
            this.lastName = lastName;
            this.left = this.right = null;
        }
    }

    public static void main(String[] args) {
        LibraryNode booksRoot = null;
        UserNode userRoot = null;
        ArrayList<LibraryNode> borrowedBooks = new ArrayList<>();
        Scanner entrada = new Scanner(System.in);

        int option;

        do {
            System.out.println("========= Harold Sanchez  1.233.496.433 =========");
            System.out.println("========= Systema gestion de Biblioteca =========");
            System.out.println("1. Menu de libros");
            System.out.println("2. Menu de usuarios");
            System.out.println("3. Prestar libros");
            System.out.println("4. Devolver libros");
            System.out.println("5. Listar libros prestados");
            System.out.println("6. Listar libros disponibles");
            System.out.println("7. Salir");
            option = entrada.nextInt();

            switch (option) {
                case 1:
                    int libraryOption;

                    do {
                        System.out.println("============== Menu de libros ==============");
                        System.out.println("1. Agregar libro");
                        System.out.println("2. Eliminar libro");
                        System.out.println("3. Listar libros");
                        System.out.println("4. Volver al menu principal");
                        System.out.println("Selecciona una opcion");

                        libraryOption = entrada.nextInt();
                        entrada.nextLine();

                        if (libraryOption == 1) {
                            System.out.println("Ingrese el ID del libro: ");
                            String id = entrada.nextLine();

                            if (searchBook(booksRoot, id)) {
                                System.out.println("El libro con el ID " + id + " ya existe.");
                            } else {
                                System.out.println("Ingrese el nombre del libro");
                                String bookName = entrada.nextLine();

                                System.out.println("Ingrese el autor del libro");
                                String author = entrada.nextLine();

                                LibraryNode newBook = new LibraryNode(id, bookName, author);
                                booksRoot = addBook(booksRoot, newBook);
                                System.out.println("Libro agregado exitosamente!");
                            }
                        } else if (libraryOption == 2) {
                            System.out.println("Ingrese el ID del libro: ");
                            String idDelete = entrada.nextLine();

                            if (searchBook(booksRoot, idDelete)) {
                                booksRoot = deleteBook(booksRoot, idDelete);
                                System.out.println("Libro eliminado");
                            } else {
                                System.out.println("El libro con el ID " + idDelete + " no se puede eliminar.");
                            }
                        } else if (libraryOption == 3) {
                            System.out.println("Libros en la biblioteca");
                            System.out.printf("%-10s %-20s %-20s%n", "ID", "Name", "Author");
                            listBooks(booksRoot);
                        } else {
                            System.out.println("Opcion no valida. Intente de nuevo!");
                        }

                    } while (libraryOption != 4);

                    break;
                case 2:
                    int userOption;

                    do {
                        System.out.println("============== Menu de Usuarios ==============");
                        System.out.println("1. Agregar usuario");
                        System.out.println("2. Eliminar usuario");
                        System.out.println("3. Listar usuarios");
                        System.out.println("4. Volver al menu principal");
                        System.out.println("Selecciona una opcion");

                        userOption = entrada.nextInt();
                        entrada.nextLine();

                        if (userOption == 1) {
                            System.out.println("Ingrese la cedula del usuario: ");
                            int cedula = entrada.nextInt();
                            entrada.nextLine();

                            if (searchUser(userRoot, cedula)) {
                                System.out.println("El usuario con la cedula " + cedula + " ya existe.");
                            } else {
                                System.out.println("Ingrese el nombre del usaurio");
                                String userName = entrada.nextLine();

                                System.out.println("Ingrese los apellidos del usaurio");
                                String lastName = entrada.nextLine();

                                UserNode newUser = new UserNode(cedula, userName, lastName);

                                if (userRoot == null) {
                                    userRoot = newUser;
                                } else {
                                    UserNode current = userRoot;
                                    while (true) {
                                        if (cedula < current.cedula) {
                                            if (current.left == null) {
                                                current.left = newUser;
                                                break;
                                            }

                                            current = current.left;
                                        } else {
                                            if (current.right == null) {
                                                current.right = newUser;
                                                break;
                                            }

                                            current = current.right;
                                        }
                                    }
                                }

                                System.out.println("Usuario agregado exitosamente!");
                            }
                        } else if (userOption == 2) {
                            System.out.println("Ingrese la cedula del usuario a eliminar: ");
                            int cedulaDelete = entrada.nextInt();

                            if (searchUser(userRoot, cedulaDelete)) {
                                userRoot = deleteUser(userRoot, cedulaDelete);
                                System.out.println("Usuario eliminado exitosamente");
                            } else {
                                System.out.println("El usuario con la cedula " + cedulaDelete + " no se puede eliminar.");
                            }
                        } else if (userOption == 3) {
                            System.out.println("Usuarios en la biblioteca");
                            System.out.printf("%-10s %-15s %-20s%n", "Cedula", "Name", "Last Name");
                            listUser(userRoot);
                        } else {
                            System.out.println("Opcion no valida. Intente de nuevo!");
                        }

                    } while (userOption != 4);

                    break;
                case 3:
                    System.out.println("Ingresar el ID del libro");
                    String idLending = entrada.next();

                    System.out.println("Ingrese la Cedula del usuario");
                    int cedulaLending = entrada.nextInt();
                    entrada.nextLine();

                    if (searchBook(booksRoot, idLending) && searchUser(userRoot, cedulaLending)) {
                        LibraryNode borrowedBook = getBook(booksRoot, idLending);

                        if (borrowedBook != null) {
                            borrowedBooks.add(borrowedBook);
                            booksRoot = deleteBook(booksRoot, idLending);

                            System.out.println(String.format("Libro %s prestado exitosamente al usuario con cedula %d", idLending, cedulaLending));
                        } else {
                            System.out.println("Error: No se encontro el libro a prestar");
                        }
                    } else {
                        System.out.println("No se puede prestar el libro: verificar que el libro y el usuario existan");
                    }

                    break;
                case 4:
                    System.out.println("Ingrese el ID del libro a devolver: ");
                    String returnId = entrada.next();

                    LibraryNode returnBook = null;

                    for (LibraryNode book : borrowedBooks) {
                        if (book.id.equals(returnId)) {
                            returnBook = book;
                            break;
                        }
                    }

                    if (borrowedBooks != null) {
                        booksRoot = addBook(booksRoot, returnBook);
                        borrowedBooks.remove(returnBook);
                        System.out.println(String.format("Libro %s (%s) devuelto exitosamente", returnBook.id, returnBook.bookName));
                    } else {
                        System.out.println("El libro no se encuentra en el registro de libros a devolver");
                    }

                    break;
                case 5:
                    if (borrowedBooks.isEmpty()) {
                        System.out.println("No hay libros prestados actualmente");
                    } else {
                        System.out.println("Libros prestados: ");
                        System.out.printf("%-10s %-20s %-20s%n", "ID", "Name", "Author");

                        for (LibraryNode book : borrowedBooks) {
                            System.out.printf("%-10s %-20s %-20s%n", book.id, book.bookName, book.author);
                        }
                    }

                    break;
                case 6:
                    System.out.println("Libros disponibles para prestar: ");
                    System.out.printf("%-10s %-20s %-20s%n", "ID", "Name", "Author");

                    listBooks(booksRoot);
                    break;
                case 7:
                    System.out.println("Saliste del aplicativo!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo!");
                    break;
            }

        } while (option != 7);

    }

    public static boolean searchBook(LibraryNode nodo, String id) {
        if (nodo == null) {
            return false;
        }
        if (id.equals(nodo.id)) {
            return true;
        }

        return id.compareTo(nodo.id) < 0 ? searchBook(nodo.left, id) : searchBook(nodo.right, id);
    }

    public static LibraryNode addBook(LibraryNode root, LibraryNode newBook) {
        if (root == null) {
            return newBook;
        }

        if (newBook.id.compareTo(root.id) < 0) {
            root.left = addBook(root.left, newBook);
        } else {
            root.right = addBook(root.right, newBook);
        }

        return root;
    }

    public static LibraryNode deleteBook(LibraryNode node, String id) {
        if (node == null) {
            return null;
        }
        if (id.compareTo(node.id) < 0) {
            node.left = deleteBook(node.left, id);
        } else if (id.compareTo(node.id) > 0) {
            node.right = deleteBook(node.right, id);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
        }

        return node;
    }

    public static void listBooks(LibraryNode node) {
        if (node != null) {
            listBooks(node.left);
            System.out.printf("%-10s %-20s %-20s%n", node.id, node.bookName, node.author);
            listBooks(node.right);
        }
    }

    public static boolean searchUser(UserNode nodo, int cedula) {
        if (nodo == null) {
            return false;
        }
        if (cedula == nodo.cedula) {
            return true;
        }

        return cedula < nodo.cedula ? searchUser(nodo.left, cedula) : searchUser(nodo.right, cedula);
    }

    public static UserNode deleteUser(UserNode node, int cedula) {
        if (node == null) {
            return null;
        }
        if (cedula < node.cedula) {
            node.left = deleteUser(node.left, cedula);
        } else if (cedula > node.cedula) {
            node.right = deleteUser(node.right, cedula);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
        }

        return node;
    }

    public static void listUser(UserNode node) {
        if (node != null) {
            listUser(node.left);
            System.out.printf("%-10s %-20s %-20s%n", node.cedula, node.userName, node.lastName);
            listUser(node.right);
        }
    }

    public static LibraryNode getBook(LibraryNode node, String id) {
        if (node == null) {
            return null;
        }
        if (id.equals(node.id)) {
            return node;
        }
        return id.compareTo(node.id) < 0 ? getBook(node.left, id) : getBook(node.right, id);
    }
}
