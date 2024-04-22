package de.rollocraft.lobbySystem.Minecraft.Utils;

import de.rollocraft.lobbySystem.Minecraft.Utils.Enum.CreatorSettings;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;

public class BookCreator {

    public static Book generateBook() {
        Book.Builder book = Book.builder()
                .title(Component.text("Creator Settings"))
                .author(Component.text("BookTuve"));

        for (CreatorSettings.Settings setting : CreatorSettings.Settings.values()) {
            book.addPage(Component.text("Setting: " + setting.name()));
            book.addPage(Component.text("Description: " + setting.name()));
            book.addPage(Component.text("State: " + setting.name()));
        }


        return book.build();
    }

    public static Book generateBook2(CreatorSettings.Pages page) {

        Book.Builder book = Book.builder()
                .title(Component.text("Creator Settings"))
                .author(Component.text("BookTuve"));

        Component pageComponent = Component.empty();

        pageComponent = pageComponent.append(page.getHeader());

        // Loop through settings on the page
        for (CreatorSettings.Settings setting : page.getSettings()) {
            pageComponent = pageComponent.append(Component.text(setting.getName()).hoverEvent(HoverEvent.showText(setting.getTooltip())));
            pageComponent = pageComponent.append(Component.newline());

            // Loop through the states of the setting
            for (CreatorSettings.State state : setting.getStates()) {
                pageComponent = pageComponent.append(state.getNameActive());
            }

        }

        book.addPage(pageComponent);

        return book.build();
    }

}
