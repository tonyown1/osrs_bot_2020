package scripts;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.Game;
import scripts.tasks.*;

import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name="Woodcutting", description="Woodcut bot", properties="client=4; author=Antoine; topic=999;")

public class Woodcutting extends PollingScript<ClientContext> {

    List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start() {
        taskList.add(new Drop(ctx));
        taskList.add(new Chop(ctx));
    }

    @Override
    public void poll() {
        ctx.game.tab(Game.Tab.INVENTORY);
        for(Task task : taskList) {
            if(ctx.controller.isStopping()) {
                break;
            }

            if(task.activate()) {
                task.execute();
                break;
            }
        }
    }
}
