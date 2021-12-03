package co.prosegur.servicioAtestados;

import java.io.OutputStream;
import java.io.PrintStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class ServicioAtestadosApplication {

	public static void main(String[] args) {

		PrintStream originalStream = System.out;		
		
		PrintStream dummyStream = new PrintStream(new OutputStream(){
		    public void write(int b) {
		        // NO-OP
		    }
		});

		System.setOut(dummyStream);

		System.setOut(originalStream);
		
		SpringApplication.run(ServicioAtestadosApplication.class, args);
	
	}
	
}
