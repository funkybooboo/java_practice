public class ComputerVoice {
    public enum Voice {Alex, Fred, Samantha, Victoria}

    public static void main(String[] args) {
        ComputerVoice computerVoice = new ComputerVoice();
        computerVoice.say("Fran has a s");
        //computerVoice.say("How are you?", Voice.Samantha);
    }

    public void say(String msg) {
        say(msg, Voice.Alex);
    }

    public void say(String msg, Voice voice) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // The shell command to run
        processBuilder.command("bash", "-c", "say -v " + voice.name() + " \"" + msg + "\"");

        try {
            // Execute the command
            Process process = processBuilder.start();
            // Wait for the command to finish
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
