package com.hexlindia.drool.common.datamigration.test.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.hexlindia.drool.common.data.doc.ProductRef;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ChangeLog
public class VideoCollectionChangeLog {

    @ChangeSet(order = "001", id = "video", author = "")
    public void importantWorkToDo(MongoTemplate mongoTemplate) {
        VideoDoc videoDoc1 = new VideoDoc("review", "KAY BEAUTY by Katrina Kaif Unbaised/Honest REVIEW | Anindita Chakravarty", "KAY BEAUTY by Katrina Kaif Unbaised/Honest REVIEW | Anindita Chakravarty Hey guys! so katrina kaif launched her makeup line with nykaa & you guys requested me to review the products. I hope this video is helpful. xx\n" +
                "\n" +
                "LINKS-\n" +
                "KAY BEAUTY- https://nyk0.page.link/jisk\n" +
                "kay kohlstar kajal- spade\n" +
                "https://nyk0.page.link/99mP\n" +
                "Kay matte action lip liner- romance\n" +
                "https://nyk0.page.link/vRgT\n" +
                "Kay matteinee lip crayon- Faux pas & wee hours\n" +
                "https://nyk0.page.link/M496\n" +
                "\n" +
                "music- Song: Hold Me Majestic Color\n" +
                "Music promoted by Majestic Casual\n" +
                "Video Link: https://youtu.be/vf_S0-V6wTE", "AU3h3qPK2tU",
                Arrays.asList(new ProductRef("p123", "Blue De Chanel Eau De Parfum", "fragrance")),
                new UserRef("1", "shabana"));
        videoDoc1.setLikes(4000);
        videoDoc1.setViews(124308);
        videoDoc1.setActive(true);
        videoDoc1.setDatePosted(LocalDateTime.now());

        VideoComment videoComment11 = new VideoComment(new UserRef("1", "shabanastyle"), LocalDateTime.now(), "ALSO pls stop this behaviour, you can't look this good ok, teach me makeup");
        videoComment11.setLikes(368);
        VideoComment videoComment12 = new VideoComment(new UserRef("1", "shabanastyle"), LocalDateTime.now(), "I  liked your review... And also agree with the price point it's really expensive,  but just wanna say don't compare Kay beauty products packaging to Jeffree star and others... Makeup Brand should reflect the personality of a person.. Katrina Kaif is a person who likes her makeup to be simple and minimalistic so she wants that her minimalistic personality should reflect in her brand ..");
        videoComment12.setLikes(3533);
        VideoComment videoComment13 = new VideoComment(new UserRef("2", "priya21"), LocalDateTime.now(), "For a celebrity makeup line the prices are so affordable.. otherwise you can see kylie, kim and other big youtubers who launch their makeup line and their prices are sky-high");
        videoComment13.setLikes(133);
        VideoComment videoComment14 = new VideoComment(new UserRef("3", "sonam31"), LocalDateTime.now(), "Instead of collaborating with Nyka people should collaborate with sugar!! Sugar cosmetics are so much better and underrated for some reason.");
        videoComment14.setLikes(76);
        List<VideoComment> videoCommentList1 = Arrays.asList(videoComment11, videoComment12, videoComment13, videoComment14);
        videoDoc1.setVideoCommentList(videoCommentList1);


        VideoDoc videoDoc2 = new VideoDoc("guide", "How To Apply Lakme Perfecting Liquid Foundation || How I Make It Full Coverage", "Product Links\n" +
                "\n" +
                "Stay Quirky Makeup Primer (27 g)\n" +
                "https://mlpl.link/3ut3A\n" +
                "\n" +
                "Lakme Perfecting Liquid Foundation Shell (27 ml)\n" +
                "https://mlpl.link/8EYwN\n" +
                "\n" +
                "Stay Quirky Translucent Powder, Longer Love Makin' - Bangin' Hard (8 g)\n" +
                "https://mlpl.link/7dJ1E\n" +
                "\n" +
                "Stay Quirky Translucent Powder, Yellow, Longer Love Makin' - Hit It (8 g)\n" +
                "https://mlpl.link/DTbVU\n" +
                "\n" +
                "Stay Quirky Translucent Powder, Pearl, Longer Love Makin' - Put Out (8 g)\n" +
                "https://mlpl.link/FC9sF\n" +
                "\n" +
                "Stay Quirky Translucent Powder, Longer Love Makin' - Get in There (8 g)\n" +
                "https://mlpl.link/xDyeG\n" +
                "\n" +
                "Stay Quirky Translucent Powder, Longer Love Makin' - Wanna XXX You Up (8 g)\n" +
                "https://mlpl.link/z1jsV\n" +
                "\n" +
                "Stay Quirky Translucent Powder, Longer Love Makin' - Get Down & Dirty (8 g)\n" +
                "https://mlpl.link/IsXLO\n" +
                " Love,\n" +
                " Chicku\n" +
                "Xoxo\n" +
                "\n" +
                "All the contents (from thumbnail to video) have been created by me,Deepanwita Dutta©. Don't use anything without my permission.The information provided on this channel and its videos is for general purposes only and shouldn't be conisidered as proffesional advice. We are trying to provide a perfect, valid, specific, detail information.Make Sure to Do a patch test each time before trying a new product or any kind Of DIY. \uD83D\uDE18Thanks for watching \uD83D\uDE18", "QW46ldTDiBY",
                Arrays.asList(new ProductRef("p123", "L O'real Clossal Eyeliner", "eyeliner"), new ProductRef("p456", "Chambor Black Eyeliner", "eyeliner")),
                new UserRef("456", "priyanaka"));
        videoDoc2.setLikes(29000);
        videoDoc2.setViews(3718868);
        videoDoc2.setActive(true);
        videoDoc2.setDatePosted(LocalDateTime.now());

        VideoComment videoComment21 = new VideoComment(new UserRef("1", "shabanastyle"), LocalDateTime.now(), "It looks fine on camera but we all know how in reality a lighter foundation makes us look. It definitely makes us grey or look whitish");
        videoComment21.setLikes(10);
        VideoComment videoComment22 = new VideoComment(new UserRef("1", "shabanastyle"), LocalDateTime.now(), "Mam daily makeup karte h to kya or kaise skin ko clean n hydrate rakhe, acne prone skin");
        videoComment22.setLikes(10);
        VideoComment videoComment23 = new VideoComment(new UserRef("1", "priya21"), LocalDateTime.now(), "Always make up  free skin is best.  Make up spoils the real beauty ...Be natural..Don't use these kind of make up products.. Full of chemicals. Good for nothing.");
        videoComment23.setLikes(830);
        VideoComment videoComment24 = new VideoComment(new UserRef("3", "sonam31"), LocalDateTime.now(), "The shade is too light");
        videoComment24.setLikes(5600);
        VideoComment videoComment25 = new VideoComment(new UserRef("3", "sonam31"), LocalDateTime.now(), "It'll be good to see in phone camera, but in reality it'll look like a joker.... Experienced");
        videoComment25.setLikes(1240);
        List<VideoComment> videoCommentList2 = Arrays.asList(videoComment21, videoComment22, videoComment23, videoComment24, videoComment25);
        videoDoc2.setVideoCommentList(videoCommentList2);

        List<VideoDoc> videoDocs = Arrays.asList(videoDoc1, videoDoc2);

        mongoTemplate.insertAll(videoDocs);
    }
}
