import exceptions.CompilerException;
import model.BaseEntity;
import model.Tuple;
import repo.RepoInter;
import repo.StatementRepo;
import service.ReadProgram;
import service.Service;

import java.io.FileNotFoundException;
import java.util.Comparator;

public class Main {

    public static void main(String[] args)
    {
        String code = readFile(args);

        RepoInter<Tuple> tupleRepoInter = new StatementRepo();
        ReadProgram readProgram = new ReadProgram(tupleRepoInter);
        try {
            readProgram.readCode(code);
        } catch (CompilerException e) {
            System.out.println(e.toString());
        }
        ((StatementRepo) tupleRepoInter).getAll().stream().sorted(Comparator.comparing(BaseEntity::getId)).forEach(System.out::println);
    }

    private static String readFile(String[] args){
        if (args.length < 1) {
            System.out.println("Not enought parameters !");
            System.exit(1);
        }
        Service service = new Service();
        try {
            return service.readFromFile(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist !");
            System.exit(2);
        }
        return null;
    }
}
