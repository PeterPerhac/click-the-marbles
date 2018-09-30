package com.perhac.games.clickthemarbles.scene.sprite;

import pulpcore.scene.Scene2D;

public class CloudBuilder {
    
    private CloudBuilder(){}
    
    public static void addClouds(Scene2D scene){
	Cloud.newCloud(scene, 100, 16500, 3000, true);
	Cloud.newCloud(scene, 80, 17000, 1000);
	Cloud.newCloud(scene, 125, 20000, 7000, true);
	Cloud.newCloud(scene, 105, 15500, 5500);
	Cloud.newCloud(scene, 75, 27000, 0, true);
	Cloud.newCloud(scene, 100, 18000, 4000);
	Cloud.newCloud(scene, 130, 16500, 9500);
	Cloud.newCloud(scene, 95, 18000, 12000, true);
    }

}
