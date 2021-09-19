package cn.itscloudy.snippedjava.pattern.flag.example2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountFlagTest {

    private final Role boss = new Role(1, "MANAGER");
    private final Role manager = new Role(2, "MANAGER");
    private final Role worker = new Role(3, "WORKER");

    @Test
    void shouldAddRolesToAccount() {
        Account account = new Account();
        account.addFlag(boss).addFlag(manager);

        assertEquals(boss.flagValue() + manager.flagValue(), account.currentFlags());
        assertTrue(boss.match(account.currentFlags()));
        assertTrue(manager.match(account.currentFlags()));
        assertFalse(worker.match(account.currentFlags()));

        assertFalse(boss.antiMatch(account.currentFlags()));
        assertFalse(manager.antiMatch(account.currentFlags()));
        assertTrue(worker.antiMatch(account.currentFlags()));
    }

    @Test
    void shouldRemoveRolesFromAccount() {
        Account account = new Account();
        account.addFlag(boss).addFlag(manager).addFlag(worker);
        account.removeFlag(boss).removeFlag(manager);

        assertEquals(worker.flagValue(), account.currentFlags());
        assertFalse(boss.match(account.currentFlags()));
        assertFalse(manager.match(account.currentFlags()));
        assertTrue(worker.match(account.currentFlags()));

        assertTrue(boss.antiMatch(account.currentFlags()));
        assertTrue(manager.antiMatch(account.currentFlags()));
        assertFalse(worker.antiMatch(account.currentFlags()));
    }

}
