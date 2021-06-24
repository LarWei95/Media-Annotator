import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import control.io.AnnotationIO;
import model.annotation.Annotation;

public class TestMain {

	public static void main(String[] args) throws Exception{
		File file = new File("E:\\Test\\test.json");
		
		HashMap<Path, Annotation> annotations = AnnotationIO.load(file);
		
		for (Path path: annotations.keySet()) {
			System.out.println(path.toString()+": "+annotations.get(path).getJsonable().toJson());
		}
	}

}
