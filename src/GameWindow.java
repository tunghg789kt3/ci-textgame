
import bases.GameObject;
import bases.events.EventManager;
import bases.inputs.CommandListener;
import bases.inputs.InputManager;
import bases.uis.InputText;
import bases.uis.StatScreen;
import bases.uis.TextScreen;
import novels.Choice;
import novels.Story;
import settings.Settings;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.nanoTime;

/**
 * Created by huynq on 7/28/17.
 */
public class GameWindow extends JFrame {

    private BufferedImage backBufferImage;
    private Graphics2D backBufferGraphics;

    private int playerX = 2;
    private int playerY = 3;
    private int mapWidth = 7;
    private int mapHeight = 7;
    HashMap<String, Story> storyMap = new HashMap<>();
    private Story currentStory;

    private void showMap() {
        EventManager.pushUIMessageNewLine("");
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (x == playerX && y == playerY) {
                    EventManager.pushUIMessage(" @ ");
                } else {
                    EventManager.pushUIMessage(" X ");
                }
            }
            EventManager.pushUIMessageNewLine("");
        }
    }

    void changeStory(String newStoryId){
        currentStory = storyMap.get(newStoryId);
        EventManager.pushUIMessageNewLine(currentStory.text);
    }
    public void otherInput(){

    }

    private long lastTimeUpdate = -1;

    public GameWindow() {
        setupFont();
        setupPanels();
        setupWindow();


        Story story1 = new Story(
                "E0001",
                "Tiếng kẽo kẹt buông xuống từ trần nhà, kéo theo chùm sáng lờ mờ quanh căn phòng. Không dễ để nhận ra có bao nhiêu người đang ở trong căn phòng này.\n" +
                "Trong lúc lần mò xung quanh căn phòng, Có tiếng nói vang lên :\n" +
                "Này chàng trai trẻ, chàng trai tên là gì vậy?",
                new Choice("sang","E0002"),
                new Choice("other","E0003")
        );

        Story story2 = new Story(
                "E0002",
                "Được đó quả là 1 cái tên kiên cường và sáng sủa.\n" +
                        "Không biết cậu có nhớ mình đã từng là 1 chiến binh vĩ đại đến từ đế quốc Neflim cổ xưa hay chăng ?\n" +
                ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes","E0010"),
                new Choice("No","E0005")
        );

        Story story3 = new Story(
                "E0003",
                "Cậu có nhầm không ? Chắc các viên đá đã làm cậu mất trí rồi. Cậu cố nhớ lại xem.\n" +
                        "Tên cậu là ?",
                new Choice("sang","E0002"),
                new Choice("other","E0004")
        );
        Story story4 = new Story(
                "E0004",
                "Không thể như vậy được. Thần Murk không nói sai, tên cậu không thể là  như vậy được.\n" +
                        "Này chàng trai trẻ tên cậu có phải là \"Sang\" không ? Hay tên cậu là ? ",
                new Choice("sang", "E0002"),
                new Choice("other", "E0004")
        );
        Story story5 = new Story(
                "E0005",
                "Đế quốc Neflim là đế chế hùng mạnh bao phủ toàn cõi lục địa này cách đây hàng ngàn năm trước.\n" +
                        "Thậm chí lực lượng của đế chế còn làm tộc Rồng có vài phần chú ý.",
                new Choice("timeOut??? Nani?","E0006")
        );
        Story story6 = new Story(
                "E0006",
                "Hắn biết cốt lõi của sức manh này chính là niềm tin vững chãi vào đấng sáng thế. Do vậy còn ai có thể củng cố niềm tin vào ngài khi mà còn đang mải đấu đá lẫn nhau.\n" +
                        "Dần dần thế giới suy tàn và những thế lực hắc ám nổi dậy. Toàn bộ nhân loại đã gần như bị tiêu diệt nếu như không có sự giúp đợ của :\n" +
                        "Noah và con tàu của ông ấy.",
                new Choice("timeOut??? Nani?", "E0007")
        );
        Story story7 = new Story(
                "E0007",
                "Uhm! 1 câu hỏi nhỏ cho cậu nhé. Theo cậu với tính cách vẫn như bây giờ thì lúc đó cậu đang là :\n" +
                        "A  - Chiến Binh\n" +
                        "B - Đạo tặc\n" +
                        "C - Pháp sư\n" +
                        "D - Cung thủ\n" +
                        "E - Võ sư\n" +
                        "F - Kiếm sư\n" +
                        "G - Triệu hồi sư\n" +
                        "H - Y sư\n" +
                        "I - Dân thường",
                new Choice("A","E0008"),
                new Choice("B","E0008"),
                new Choice("C","E0008"),
                new Choice("D","E0008"),
                new Choice("F","E0008"),
                new Choice("G","E0008"),
                new Choice("H","E0008"),
                new Choice("I","E0009"),
                new Choice("other","E0008")
        );
        Story story8 = new Story(
                "E0008",
                "Nhầm rồi, cậu đoán lại nhé. Theo cậu với tính cách vẫn như bây giờ thì lúc đó cậu đang là :\n" +
                        "A  - Chiến Binh\n" +
                        "B - Đạo tặc\n" +
                        "C - Pháp sư\n" +
                        "D - Cung thủ\n" +
                        "E - Võ sư\n" +
                        "F - Kiếm sư\n" +
                        "G - Triệu hồi sư\n" +
                        "H - Y sư\n" +
                        "I - Dân thường",
                new Choice("A","E0008"),
                new Choice("B","E0008"),
                new Choice("C","E0008"),
                new Choice("D","E0008"),
                new Choice("F","E0008"),
                new Choice("G","E0008"),
                new Choice("H","E0008"),
                new Choice("I","E0009"),
                new Choice("other","E0008")

        );
        Story story9 = new Story(
                "E0009",
                "Chính xác.! cậu quả là người không ngu thì khôn vô cùng, chả mấy chốc đã đoán ra mình là ai.\n" +
                        "Là dân thường cậu đã thấm nhuần nỗi khổ nhưng lại có đức hi sinh cùng lòng vị tha. Đó là tất cả những phẩm chất cần có của 1 người hùng nắm giữ sức mạnh của ánh sáng.",
                new Choice("timeOut??? Nani?", "E0010")
        );
        Story story10 = new Story(
                "E0010",
                "Sau hàng tháng chiến đấu liên tiếp không nghỉ cậu đã cầm chân đủ lâu lũ quái vật để cho Noah trốn thoát chỉ với 1 lời \"Xin lỗi!\".",
                new Choice("timeOut??? Nani?", "E0007")
        );
        Story story11 = new Story(
                "E0011",
                "Chính vì cậu là 1 người anh Hùng như thế nên không biết cậu có ý cứu giúp thế giới này 1 lần nữa hay không ?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0012")
        );
        Story story12 = new Story(
                "E0012",
                "Sao cậu lại nỡ từ chối lời thỉnh cầu của lão già này?\n" +
                        "Mong cậu nghĩ lại dùm được không ?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0013")

        );
        Story story13 = new Story(
                "E0013",
                "Có lẽ nào cậu bỏ mặc hàng ngàn người chết đói ở Phi Châu sao. Tất cả họ đang chờ 1 cái gật đầu của cậu. Cậu chỉ cần like 1 cái là xong?\n" +
                        "Cậu suy nghĩ lại chứ ?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0014")

                );
        Story story14 = new Story(
                "E0014",
                "Không tính người Phi Châu đi, vậy cậu có nghĩ tới hàng ngàn hàng trăm bà mẹ Việt nam anh hùng đã hi sinh chồng, con để bảo vệ đất nước này sao ?\n" +
                        "Cậu suy nghĩ lại chứ ?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0015")

                );
        Story story15 = new Story(
                "E0015",
                "Vậy còn Gấu thì sao, cậu đang tâm để cho lũ quỷ sai giày xé cơ thể  lẫn tâm hồn mỏng manh dễ vỡ của Gấu hay sao?\n" +
                        "Cậu suy nghĩ lại chứ?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0016")

                );
        Story story16 = new Story(
                "E0016",
                "Thôi thì Gấu bạc, gấu phản cậu thì cậu cho qua đi. vậy còn các em gái xinh tươi, mơn mởn khác sẽ vuột khỏi tay cậu sao ?\n" +
                        "Cậu suy nghĩ lại chư?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0017")

                );
        Story story17 = new Story(
                "E0017",
                "Không ? KHÔNG ? Cậu từ chối trách nhiệm của mình sao ?\n" +
                        "Ta cho cậu cơ hội cuối cùng để chọn, Có hoặc Không ?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0018")

                );
        Story story18 = new Story(
                "E0018",
                "Vẫn là Không, TA KHÔNG THỂ NGỜ LẠI CÓ 1 TÊN VÔ LẠI NHƯ CẬU LẠI CÓ THỂ THỪA HƯỞNG DÒNG MÁU DANH GIÁ ẤY!\n" +
                        "Nể tình anh em với LightLord ta cho cậu thêm 1 cơ hội nữa! Có hoặc Không?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0019")

                );
        Story story19 = new Story(
                "E0019",
                "Thôi , tôi xin cậu mà, cậu giúp cho, vạn lần mong cậu rủ lòng thương cho cái xã hội thối nát này.\n" +
                        "Mong cậu nghĩ lại cho!\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "E0020")

                );
        Story story20 = new Story(
                "E0020",
                "Hu, hu, hu, hu cậu nỡ lòng nào nhìn thế giới này đi vào suy tàn. cậu nỡ lòng nào không cho tôi cơ hội ăn Phở nữa.\n" +
                        "À .. nhầm ... không cho tôi cơ hội ngắm nhìn ánh mặt trời này nữa.\n" +
                        "Liệu cậu nghĩ lại chứ ?\n" +
                        ";#00FF00Yes; or ;#FF0000No; ?",
                new Choice("Yes", "E0021"),
                new Choice("No", "Nani?")

                );
        Story story21 = new Story(
                "E0021",
                "Một hành trình mới. ... etc",
                new Choice("", "")

                );
        Story storyNani = new Story(
                "Nani?",
                "\"Không Không CLGT\" dậy đi sửa mạng đi mày!\n" +
                        "Sáng nửa tỉnh nửa mê lật đật bỏ đi. Không màng bận tâm đến giấc mơ kỳ lạ vừa rồi.",
                new Choice("","")

                );

        storyMap.put(story1.id, story1);
        storyMap.put(story2.id, story2);
        storyMap.put(story3.id, story3);
        storyMap.put(story4.id, story4);
        storyMap.put(story5.id, story5);
        storyMap.put(story6.id, story6);
        storyMap.put(story7.id, story7);
        storyMap.put(story8.id, story8);
        storyMap.put(story9.id, story9);
        storyMap.put(story10.id, story10);
        storyMap.put(story11.id, story11);
        storyMap.put(story12.id, story12);
        storyMap.put(story13.id, story13);
        storyMap.put(story14.id, story14);
        storyMap.put(story15.id, story15);
        storyMap.put(story16.id, story16);
        storyMap.put(story17.id, story17);
        storyMap.put(story18.id, story18);
        storyMap.put(story19.id, story19);
        storyMap.put(story20.id, story20);
        storyMap.put(story21.id, story21);
        storyMap.put(storyNani.id, storyNani);

        currentStory = story1;

        EventManager.pushUIMessageNewLine(currentStory.text + "\n");

        InputManager.instance.addCommandListener(new CommandListener() {
            @Override
            public void onCommandFinished(String command) {
                EventManager.pushUIMessageNewLine("");
                EventManager.pushUIMessageNewLine(command);
                EventManager.pushUIMessageNewLine("");
//                if (command.equalsIgnoreCase("map")) {
//                    showMap();
//                } else if (command.equalsIgnoreCase("right")) {
//                    if (playerX == mapWidth - 1) {
//                        EventManager.pushUIMessageNewLine("Your way ;#FF0000blocked; by Wall, please choose another way!!!");
//                    } else {
//                        playerX++;
//                        EventManager.pushUIMessageNewLine("\nYou just go ;#00ff00right; 1 block");
//                        showMap();
//                    }
//                }
                for (Choice choice : currentStory.choises){
                    if (choice.match(command)){
                        changeStory(choice.to);
                        break;
                    }
                }
            }

            @Override
            public void commandChanged(String command) {

            }
        });
    }

    private void setupFont() {

    }

    private void setupPanels() {
        TextScreen textScreenPanel = new TextScreen();
        textScreenPanel.setColor(Color.DARK_GRAY);
        textScreenPanel.getSize().set(
                Settings.TEXT_SCREEN_SCREEN_WIDTH,
                Settings.TEXT_SCREEN_SCREEN_HEIGHT);
        pack();
        textScreenPanel.getOffsetText().set(getInsets().left + 20, getInsets().top + 20);
        GameObject.add(textScreenPanel);


        InputText commandPanel = new InputText();
        commandPanel.getPosition().set(
                0,
                Settings.SCREEN_HEIGHT
        );
        commandPanel.getOffsetText().set(20, 20);
        commandPanel.getSize().set(
                Settings.CMD_SCREEN_WIDTH,
                Settings.CMD_SCREEN_HEIGHT
        );
        commandPanel.getAnchor().set(0, 1);
        commandPanel.setColor(Color.DARK_GRAY);
        GameObject.add(commandPanel);


        StatScreen statsPanel = new StatScreen();
        statsPanel.getPosition().set(
                Settings.SCREEN_WIDTH,
                0
        );

        statsPanel.getAnchor().set(1, 0);
        statsPanel.setColor(Color.darkGray);
        statsPanel.getSize().set(
                Settings.STATS_SCREEN_WIDTH,
                Settings.STATS_SCREEN_HEIGHT
        );
        GameObject.add(statsPanel);

        InputManager.instance.addCommandListener(textScreenPanel);
    }


    private void setupWindow() {
        this.setSize(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        this.setVisible(true);
        this.setTitle(Settings.GAME_TITLE);
        this.addKeyListener(InputManager.instance);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        backBufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        backBufferGraphics = (Graphics2D) backBufferImage.getGraphics();
    }

    public void gameLoop() {
        while (true) {
            if (-1 == lastTimeUpdate) lastTimeUpdate = nanoTime();

            long currentTime = nanoTime();

            if (currentTime - lastTimeUpdate > 17000000) {
                lastTimeUpdate = currentTime;
                GameObject.runAll();
                InputManager.instance.run();
                render(backBufferGraphics);
                repaint();
            }
        }
    }

    private void render(Graphics2D g2d) {
        g2d.setFont(Settings.DEFAULT_FONT);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);

        GameObject.renderAll(g2d);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(backBufferImage, 0, 0, null);
    }
}
