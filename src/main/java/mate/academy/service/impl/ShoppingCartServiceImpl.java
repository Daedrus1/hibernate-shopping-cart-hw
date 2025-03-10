package mate.academy.service.impl;

import java.util.ArrayList;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.lib.Inject;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.Ticket;
import mate.academy.model.User;
import mate.academy.service.ShoppingCartService;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket ticket = new Ticket();
        ticket.setMovieSession(movieSession);
        ticket.setUser(user);

        ShoppingCart shoppingCart = getByUser(user);
        shoppingCart.getTickets().add(ticket);
        shoppingCartDao.update(shoppingCart);

    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user).orElseThrow(
                () -> new RuntimeException("No shopping cart found for user: " + user));
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTickets(new ArrayList<>());

        shoppingCartDao.add(shoppingCart);

    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getTickets().clear();
        shoppingCartDao.update(shoppingCart);

    }
}
