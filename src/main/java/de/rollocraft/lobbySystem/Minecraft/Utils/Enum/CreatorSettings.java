package de.rollocraft.lobbySystem.Minecraft.Utils.Enum;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;

public enum CreatorSettings {

    ;

    public enum State {
        ON(
                Component.text("[ON]", NamedTextColor.DARK_GREEN),
                Component.text("[ON]", NamedTextColor.DARK_GRAY),
                Component.text("")),
        RECMODE(
                Component.text(" [REC MODE]", TextColor.color(255,165,0)),
                Component.text(" [REC MODE]", NamedTextColor.DARK_GRAY),
                Component.text("")),
        FRIENDSONLY(
                Component.text(" [FRIENDS ONLY]", TextColor.color(255,165,0)),
                Component.text(" [FRIENDS ONLY]", NamedTextColor.DARK_GRAY),
                Component.text("")),
        OFF(
                Component.text(" [OFF]", NamedTextColor.RED),
                Component.text(" [OFF]", NamedTextColor.DARK_GRAY),
                Component.text(""));

        private Component nameactive;
        private Component nameinactive;
        private Component tooltip;
        State(Component nameactive, Component nameinactive, Component tooltip) {
            this.nameactive = nameactive;
            this.nameinactive = nameinactive;
            this.tooltip = tooltip;
        }

        public Component getNameActive() {
            return this.nameactive;
        }
        public Component getNameInactive() {
            return this.nameinactive;
        }
        public Component getTooltip() {
            return this.tooltip;
        }
    }
    public enum Settings {
        RECORDINGMODE(
                "\n\nRecording Mode",
                Component.empty(),
                List.of(State.ON, State.OFF)),
        ALLOWMSG(
                "\n\nAllow /msg",
                Component.empty(),
                List.of(State.ON, State.RECMODE, State.FRIENDSONLY,State.OFF)),
        SHOWJOINMESSAGE(
                "\n\nShow Join Message",
                Component.empty(),
                List.of(State.ON, State.RECMODE, State.FRIENDSONLY,State.OFF));

        private String name;
        private Component tooltip;
        private List<State> states;
        Settings(String name, Component tooltip, List<State> states) {
            this.name = name;
            this.tooltip = tooltip;
            this.states = states;
        }

        public String getName() {
            return this.name;
        }
        public Component getTooltip() {
            return this.tooltip;
        }
        public List<State> getStates() {
            return this.states;
        }
    }

    public enum Pages{
        p1(
                Component.text("Creator Settings"),
                Component.text("\n\n\n")
                        .append(Component.text("<--", Style.style(NamedTextColor.GRAY, TextDecoration.BOLD)))
                        .append(Component.text("               "))
                        .append(Component.text("-->", Style.style(NamedTextColor.BLACK, TextDecoration.BOLD))
                                .clickEvent(ClickEvent.runCommand("creatorsettings p 2"))),
                List.of(Settings.RECORDINGMODE, Settings.ALLOWMSG, Settings.SHOWJOINMESSAGE)),
        p2(
                Component.text("Creator Settings"),
                Component.empty()
                        .append(Component.text("<--", Style.style(NamedTextColor.BLACK, TextDecoration.BOLD))
                                .clickEvent(ClickEvent.runCommand("creatorsettings p 1")))
                        .append(Component.text("               "))
                        .append(Component.text("-->", Style.style(NamedTextColor.BLACK, TextDecoration.BOLD))
                                .clickEvent(ClickEvent.runCommand("creatorsettings p 3"))),
                List.of());

        private Component header;
        private Component footer;
        private List<Settings> settings;
        Pages(Component header, Component footer, List<Settings> settings) {
            this.header = header;
            this.footer = footer;
            this.settings = settings;
        }

        public Component getHeader(){
            return this.header;
        }
        public Component getFooter() {
            return this.footer;
        }
        public List<Settings> getSettings() {
            return this.settings;
        }
    }



}
