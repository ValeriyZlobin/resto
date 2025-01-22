package org.example.service.user.imp;

import org.example.service.command.Command;
import org.example.service.user.UserService;
import org.example.util.UtilInput;

import java.util.List;

public class DefaultUserService implements UserService {

    private final List<Command> commandList = List.of();

    @Override
    public void startWorkWithUser() {
        System.out.println("Добро пожаловать в ресторан!");
        boolean isWorking = chooseCommand();
        while (isWorking) {
            isWorking = chooseCommand();
        }
        System.out.println("Спасибо за визит! До новых встреч.");
    }

    private boolean chooseCommand() {
        System.out.println("Выберите команду для выполнения:");
        for (int i = 0; i < commandList.size(); i++) {
            System.out.printf("%s - %s%n", i, commandList.get(i).getCommandName());
        }
        int numberChoose = UtilInput.getRequiredIntFromUser(0, commandList.size() - 1);
        return commandList.get(numberChoose).execute();
    }
}
