package codegen;

import org.batgen.BatGen;
import org.batgen.DatabaseType;

public class CodeGen {

	public static void main( String[]args ) {
		BatGen batGen = new BatGen( "src/test/resources", "com.simoncomputing.app.winventory", DatabaseType.H2 );
		batGen.run();
	}
	
}
