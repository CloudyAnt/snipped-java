package cn.itscloudy.snippedjava.pattern.flag.example2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountFlagTest {

    private final Role boss = new Role(1, "MANAGER");
    private final Role manager = new Role(2, "MANAGER");
    private final Role worker = new Role(3, "WORKER");

    @Test
    public void shouldAddRolesToAccount() {
        Account account = new Account();
        account.addFlag(boss).addFlag(manager);

        assertEquals(boss.flagValue() + manager.flagValue(), account.getFlags());
        assertTrue(boss.match(account.getFlags()));
        assertTrue(manager.match(account.getFlags()));
        assertFalse(worker.match(account.getFlags()));

        assertFalse(boss.antiMatch(account.getFlags()));
        assertFalse(manager.antiMatch(account.getFlags()));
        assertTrue(worker.antiMatch(account.getFlags()));
    }

    @Test
    public void shouldRemoveRolesFromAccount() {
        Account account = new Account();
        account.addFlag(boss).addFlag(manager).addFlag(worker);
        account.removeFlag(boss).removeFlag(manager);

        assertEquals(worker.flagValue(), account.getFlags());
        assertFalse(boss.match(account.getFlags()));
        assertFalse(manager.match(account.getFlags()));
        assertTrue(worker.match(account.getFlags()));

        assertTrue(boss.antiMatch(account.getFlags()));
        assertTrue(manager.antiMatch(account.getFlags()));
        assertFalse(worker.antiMatch(account.getFlags()));
    }

}
