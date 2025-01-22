package org.example.service.command;
import org.example.service.order.OrderService;
import org.example.service.order.imp.DefaultOrderService;
import org.example.service.teble.TableService;
import org.example.service.teble.imp.DefaultTableService;

public abstract class AbstractCommand implements Command{

    private final String title;
    private final OrderService orderService;
    private final TableService tableService;

    protected AbstractCommand(String title) {
        this.title = title;
        this.orderService = new DefaultOrderService();
        this.tableService = new DefaultTableService();
    }

    protected OrderService getOrderService() {
        return orderService;
    }

    protected TableService getTableService() {
        return tableService;
    }

    @Override
    public String getCommandName() {
        return title;
    }

    @Override
    public String toString() {
        return "AbstractCommand{" +
                "title='" + title + '\'' +
                '}';
    }
}
