import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server started on port 8080...");

            while (true) {
                Socket socket = serverSocket.accept();
                handleClientRequest(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream outputStream = socket.getOutputStream()) {

            // Read the HTTP request
            String requestLine = reader.readLine();
            if (requestLine != null) {
                System.out.println("Received request: " + requestLine);

                // Determine the file path and content type
                String[] requestParts = requestLine.split(" ");
                String requestedFile = requestParts[1];
                String contentType = getContentType(requestedFile);

                if (requestedFile.equals("/")) {
                    serveHtmlPage(outputStream);
                } else if (requestedFile.equals("/api/message")) {
                    sendApiMessage(outputStream);
                } else {
                    // Serve static files
                    sendStaticFile(outputStream, requestedFile, contentType);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void sendStaticFile(OutputStream outputStream, String filePath, String contentType) throws IOException {
        File file = new File("src" + filePath); // Adjust path to include the src directory
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            byte[] fileData = fis.readAllBytes();
            fis.close();

            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "Content-Length: " + fileData.length + "\r\n" +
                    "\r\n";
            outputStream.write(httpResponse.getBytes());
            outputStream.write(fileData);
            outputStream.flush();
        } else {
            sendNotFoundResponse(outputStream);
        }
    }


    private static String getContentType(String fileName) {
        if (fileName.endsWith(".css")) {
            return "text/css";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else {
            return "application/octet-stream";
        }
    }



    private static void serveHtmlPage(OutputStream outputStream) throws IOException {
        File htmlFile = new File("src/index.html"); // Adjust path to include src directory
        if (htmlFile.exists()) {
            FileInputStream fis = new FileInputStream(htmlFile);
            byte[] htmlData = fis.readAllBytes();
            fis.close();

            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + htmlData.length + "\r\n" +
                    "\r\n";
            outputStream.write(httpResponse.getBytes());
            outputStream.write(htmlData);
            outputStream.flush();
        } else {
            sendNotFoundResponse(outputStream);
        }
    }


    private static void sendCssFile(OutputStream outputStream) throws IOException {
        // Load the CSS file from the classpath (src directory)
        InputStream cssStream = SimpleHttpServer.class.getResourceAsStream("/style.css");

        if (cssStream != null) {
            byte[] cssData = cssStream.readAllBytes();
            cssStream.close();

            // Send HTTP response headers for CSS
            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/css\r\n" +
                    "Content-Length: " + cssData.length + "\r\n" +
                    "\r\n";
            outputStream.write(httpResponse.getBytes());
            outputStream.write(cssData);
            outputStream.flush();
        } else {
            // Send a 404 response if the file is not found
            sendNotFoundResponse(outputStream);
        }
    }

    private static void sendImageFile(OutputStream outputStream, String imagePath) throws IOException {
        // Remove leading '/' from imagePath and locate file in the project directory
        String sanitizedPath = imagePath.substring(1); // e.g., "sliki/ketering.jpg"
        File imageFile = new File(sanitizedPath);

        if (imageFile.exists()) {
            FileInputStream fis = new FileInputStream(imageFile);
            byte[] imageData = fis.readAllBytes();
            fis.close();

            // Determine the image's content type
            String contentType = "image/jpeg"; // Default to JPEG
            if (imagePath.endsWith(".png")) {
                contentType = "image/png";
            }

            // Send HTTP response headers
            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "Content-Length: " + imageData.length + "\r\n" +
                    "\r\n";
            outputStream.write(httpResponse.getBytes());

            // Send the image file
            outputStream.write(imageData);
            outputStream.flush();
        } else {
            sendNotFoundResponse(outputStream);
        }
    }



    private static void sendApiMessage(OutputStream outputStream) throws IOException {
        String message = "Hello, this is a response from the server!";

        // Send HTTP response headers
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + message.length() + "\r\n" +
                "\r\n";
        outputStream.write(httpResponse.getBytes());

        // Send the API response
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    private static void sendNotFoundResponse(OutputStream outputStream) throws IOException {
        String errorMessage = "404 Not Found";
        String httpResponse = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + errorMessage.length() + "\r\n" +
                "\r\n";
        outputStream.write(httpResponse.getBytes());
        outputStream.write(errorMessage.getBytes());
        outputStream.flush();
    }
}
