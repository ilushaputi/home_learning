import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleApp {

    public List<String> projectList = new ArrayList<> ();
    public List<String> taskList = new ArrayList<> ();
    BufferedReader bufferedReader = new BufferedReader (new InputStreamReader (System.in));

    public static void main(String[] args) throws IOException {
        System.out.println ("*** WELCOME TO TASK MANAGER ***");
        ConsoleApp consoleApp = new ConsoleApp ();
        consoleApp.chooseAction (consoleApp.enterString ());
    }

    public String enterString() throws IOException {
        System.out.println ("Enter command, please");
        String string = bufferedReader.readLine ();
        return string;
    }

    public void chooseAction(String s) throws IOException {
        if (s.equals ("HELP")){
            System.out.println ("PROJECT CREATE: Create new project");
            System.out.println ("PROJECT VIEW: View project list");
            System.out.println ("PROJECT EDIT: Remove select project");
            System.out.println ("PROJECT CLEAR: Remove all project");
            System.out.println ("TASK CREATE: Create new task");
            System.out.println ("TASK VIEW: View task list");
            System.out.println ("TASK EDIT: Remove select task");
            System.out.println ("TASK CLEAR: Remove all task");
            System.out.println ("EXIT: Close this program");
            System.out.println ();
            chooseAction (enterString ());
        } else if (s.equals ("PROJECT CREATE")){
            createProject (projectList);
            chooseAction (enterString ());
        } else if (s.equals ("PROJECT VIEW")){
            viewProject (projectList);
            chooseAction (enterString ());
        } else if (s.equals ("PROJECT EDIT")){
            editProject (projectList);
            chooseAction (enterString ());
        } else if (s.equals ("PROJECT CLEAR")){
            removeAllProject (projectList);
            chooseAction (enterString ());
        } else if (s.equals ("TASK CREATE")){
            createTask (taskList);
            chooseAction (enterString ());
        } else if (s.equals ("TASK VIEW")){
            viewTask (taskList);
            chooseAction (enterString ());
        } else if (s.equals ("TASK EDIT")){
            editTask (taskList);
            chooseAction (enterString ());
        } else if (s.equals ("TASK CLEAR")){
            removeAllTask (taskList);
            chooseAction (enterString ());
        } else if (s.equals ("EXIT")){
            System.exit (0);
        }
        else {
            System.out.println ("Unknown command. Repeat command, please.");
            chooseAction (enterString ());
        }
    }

    public List<String> createProject(List<String> list) throws IOException {
        System.out.println ("[PROJECT CREATE]");
        System.out.println ("ENTER PROJECT NAME");
        list.add (bufferedReader.readLine ());
        System.out.println ("[OK]");
        System.out.println ();
        return list;
    }

    public List<String> createTask(List<String> list) throws IOException {
        System.out.println ("[TASK CREATE]");
        System.out.println ("ENTER TASK NAME");
        list.add (bufferedReader.readLine ());
        System.out.println ("[OK]");
        System.out.println ();
        return list;
    }

    public void  viewProject(List<String> list){
        System.out.println ("[PROJECT LIST]");
        int count = 1;
        for (String s : list) {
            System.out.println (count + ". " + s);
            count++;
        }
        System.out.println ("[OK]");
        System.out.println ();
    }

    public void  viewTask(List<String> list){
        System.out.println ("[TASK LIST]");
        int count = 1;
        for (String s : list) {
            System.out.println (count + ". " + s);
            count++;
        }
        System.out.println ("[OK]");
        System.out.println ();
    }

    public List<String> editProject(List<String> list) throws IOException {    //удаление одного проекта
        System.out.println ("[PROJECT EDIT]");
        System.out.println ("ENTER PROJECT NAME");
        String projectName = bufferedReader.readLine ();
        for (int i = 0; i < list.size (); i++){
            if (list.get (i).equals (projectName)){
                list.remove (i);
            }
        }
        System.out.println ("[OK]");
        System.out.println ();
        return list;
    }

    public List<String> editTask(List<String> list) throws IOException {    //удаление одной задачи
        System.out.println ("[TASK EDIT]");
        System.out.println ("ENTER TASK NAME");
        String taskName = bufferedReader.readLine ();
        for (int i = 0; i < list.size (); i++){
            if (list.get (i).equals (taskName)){
                list.remove (i);
            }
        }
        System.out.println ("[OK]");
        System.out.println ();
        return list;
    }

    public List<String> removeAllProject(List<String> list){  //удаление всех проектов (очистка списка проектов)
        System.out.println ("[REMOVE ALL PROJECT]");
        list.clear ();
        System.out.println ("[OK]");
        System.out.println ();
        return list;
    }

    public List<String> removeAllTask(List<String> list){  //удаление всех задач (очистка списка задач)
        System.out.println ("[REMOVE ALL TASK]");
        list.clear ();
        System.out.println ("[OK]");
        System.out.println ();
        return list;
    }
}
