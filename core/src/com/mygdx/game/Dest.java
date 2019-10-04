package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.sql.Time;
import java.util.ArrayList;

public class Dest extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img , dragonhome , bullet , oyuncusaldiri , dolucan , boscan , enemybullet , kaybet , kazan;
	int X ,homeX , dragonwidth , dragonheight , dragonY , enemywidth , enemyheight , enemyhbwidth, enemyhbheight , playerbulletwidth , playerbulletheight,patlamasayc,dusmanmermisayac , dragoncan,seviye,yokedilendusman;
	Texture[] dragons , oklar , enemies , patlamaefekti ,canlar;
	Animation ejderha , dusman;
	float elapsed;
	ArrayList<Rectangle> dusmanlar , mermiler;
	long simdikizaman , dusmanuremezaman;
	private boolean oyuncusd,oyunbitti,kazandiniz,kaybettiniz;
	private int saldirisayac;
	ArrayList<ArrayList<Integer>> enemiess;
	ArrayList<Integer> enemy;
	ArrayList<ArrayList<Integer>> patlamalar;
	ArrayList<Integer> patlama;
	ArrayList<ArrayList<Integer>> dusmanMermiler;
	ArrayList<Integer> dusmanMermi;
	TextureRegion canbolge;
	BitmapFont font ;
	Preferences level;
	int dusmanuremezamani ;
	@Override
	public void create () {
		font = new BitmapFont();
		level = Gdx.app.getPreferences("seviye");
		seviye = level.getInteger("seviye3",1);
		font.getData().setScale(3,2);
		font.setColor(Color.WHITE);
		dragoncan = 7 ;
		enemiess = new ArrayList<ArrayList<Integer>>();
		batch = new SpriteBatch();
		enemybullet = new Texture("enemybullet.png");
		img = new Texture("nightbackground.jpg");
		dragonhome = new Texture("dragonhome.png");
		oyuncusaldiri = new Texture("dragons/dragon3.png");
		dolucan = new Texture("can/can1.png");
		boscan = new Texture("can/can2.png");
		kazan = new Texture("youwin.png");
		kaybet = new Texture("gameover.png");
		dragons = new Texture[2];
		oklar = new Texture[3];
		enemies = new Texture[2];
		patlamaefekti = new Texture[33];
		canlar = new Texture[8];
		mermiler = new ArrayList<Rectangle>();
		patlamalar = new ArrayList<ArrayList<Integer>>();
		dusmanMermiler = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < dragons.length; i++) {
				dragons[i] = new Texture("dragons/dragon" + (i + 1) + ".png");
			}

		for(int i=0;i<oklar.length;i++){
			oklar[i] = new Texture("ok" + (i+1) + ".png");
		}
		for(int i=0;i<enemies.length;i++){
			enemies[i] = new Texture("enemies/fly" + (i+1) + ".png");
		}
		for(int i=0;i<patlamaefekti.length;i++){
			patlamaefekti[i] = new Texture("patlama/" + (i+1) + ".png");
		}

		for(int i=0;i<canlar.length;i++){
			canlar[i] = new Texture("dragoncan/can" + i + ".png");
		}
		ejderha = new Animation(0.4f,dragons);
		dusman = new Animation(0.4f,enemies);
		dragonwidth = 200 ; dragonheight = 200;
		enemywidth = 150 ; enemyheight = 150;
		enemyhbwidth = 150; enemyhbheight = 30;
		playerbulletwidth = 50; playerbulletheight = 50;
		dragonY = Gdx.graphics.getHeight()/2 - 100;
        dusmanlar = new ArrayList<Rectangle>();
        bullet = new Texture("bullet.png");
	}

	@Override
	public void render () {
		dusmanuremezamani = 10000 - 1000 * seviye;
		elapsed += Gdx.graphics.getDeltaTime();
		simdikizaman = TimeUtils.millis();
		batch.begin();
		batch.draw(img,X,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(img,X + Gdx.graphics.getWidth(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		font.draw(batch,"level  " + seviye,Gdx.graphics.getWidth()-150 ,Gdx.graphics.getHeight()-30);
		batch.draw(dragonhome,homeX,50,357,Gdx.graphics.getHeight()-300);
		if(!oyuncusd){
			if(!oyunbitti){
			batch.draw((Texture) ejderha.getKeyFrame(elapsed,true),0,dragonY, dragonwidth,dragonheight);}
			else{
				batch.draw(dragons[1],0,dragonY,dragonwidth,dragonheight);
			}
		}else{
			batch.draw(oyuncusaldiri,0,dragonY,dragonwidth,dragonheight);
		}
		batch.draw(oklar[0],950,59);
		batch.draw(oklar[1],1120,59);
		batch.draw(oklar[2],50,49,150,150);
		X -= 1 ;
		homeX -= 1;
		if(X + Gdx.graphics.getWidth() == 0){
			X = 0;
		}

		if(Gdx.input.isTouched()){
			if(Gdx.input.getX()>950 && Gdx.input.getX()<950 + oklar[0].getWidth()&& Gdx.graphics.getHeight()-Gdx.input.getY() > 59
			&& Gdx.graphics.getHeight() - Gdx.input.getY()<59 + oklar[0].getHeight()){
                 if(dragonY + dragonheight < Gdx.graphics.getHeight()){
                 	dragonY+=3;
				 }
			}else if(Gdx.input.getX()>1120 && Gdx.input.getX()<1120 + oklar[1].getWidth() && Gdx.graphics.getHeight()-Gdx.input.getY()>59 && Gdx.graphics.getHeight()-Gdx.input.getY()<59 + oklar[1].getHeight()){
			    if(dragonY>0){
			        dragonY-=3;
                }
            }

		}

		if(Gdx.input.justTouched()){
			if(Gdx.input.getX()>50 && Gdx.input.getX()<200 && Gdx.graphics.getHeight()-Gdx.input.getY() > 49 && Gdx.graphics.getHeight() - Gdx.input.getY() < 150){
				oyuncusd = true;
				mermiUret();
			}
		}
		if(!oyunbitti) {
			seviyeArttir();
			canCiz();
			dusmanUret();
			dusmanCanCiz();
			dusmanIlerlet();
            mermiIlerlet();
			mermiCiz();
			oyuncuSaldiriKontrol();
			dusmanSaldiri();
			dusmanYokEt();
			dusmanCiz();
			dusmanMermiCiz();
			dusmanMermiIlerlet();
			patlamaCiz();
		}
		oyunBitti();
		batch.end();
	}

	public void dusmanUret(){
		if(simdikizaman-dusmanuremezaman>dusmanuremezamani) {
			/*Rectangle dusman = new Rectangle();
			dusman.x = Gdx.graphics.getWidth();
			dusman.y = MathUtils.random(59 + oklar[0].getHeight(), Gdx.graphics.getHeight() - 150);
			dusmanlar.add(dusman);*/
			enemy = new ArrayList<Integer>();
			int enemyy = MathUtils.random(59 + oklar[0].getHeight(), Gdx.graphics.getHeight() - 150);
			enemy.add(Gdx.graphics.getWidth());
			enemy.add(enemyy);
			enemy.add(3);   //düşmanın canı
			enemiess.add(enemy);
			dusmanuremezaman = TimeUtils.millis();
		}
	}

	public void dusmanCiz(){
		/*for(int i=0;i<dusmanlar.size();i++) {
			batch.draw((Texture) dusman.getKeyFrame(elapsed, true),dusmanlar.get(i).x,dusmanlar.get(i).y,150,150);
		}*/
		for(int i=0;i<enemiess.size();i++){
			batch.draw((Texture)dusman.getKeyFrame(elapsed,true),enemiess.get(i).get(0),enemiess.get(i).get(1),enemywidth,enemyheight);
		}
	}

	public void dusmanIlerlet(){
		/*for(int i=0;i<dusmanlar.size();i++){
			dusmanlar.get(i).x--;
		}*/
		for(int i=0;i<enemiess.size();i++){
			enemiess.get(i).set(0,enemiess.get(i).get(0) - 2);
			if(enemiess.get(i).get(0) + dragonwidth<0){
			    dragoncan--;
			    enemiess.remove(i);
            }
		}
	}

	public void mermiUret(){
	    Rectangle mermi = new Rectangle();
	    mermi.x = dragonwidth;
	    mermi.y = dragonY + 55;
	    mermiler.add(mermi);
    }

    public void mermiCiz(){
	    for(int i=0;i<mermiler.size();i++){
	        batch.draw(bullet,mermiler.get(i).x,mermiler.get(i).y,playerbulletwidth,playerbulletheight);
        }
    }

    public void mermiIlerlet(){
		for(int i=0;i<mermiler.size();i++){
			mermiler.get(i).x++;
		}
	}

	public void oyuncuSaldiriKontrol() {
		if (oyuncusd) {
			saldirisayac++;
			if (saldirisayac > 10) {
				oyuncusd = false;
				saldirisayac = 0;
			}
		}
	}

	public void dusmanCanCiz(){
		for(int i=0;i<enemiess.size();i++){
            batch.draw(boscan, enemiess.get(i).get(0), enemiess.get(i).get(1) - enemyhbheight, enemyhbwidth, enemyhbheight);
			if(enemiess.get(i).get(2)==3) {
			    canbolge = new TextureRegion(dolucan,0,0,dolucan.getWidth(),dolucan.getHeight());
				batch.draw(canbolge, enemiess.get(i).get(0), enemiess.get(i).get(1) - enemyhbheight, enemyhbwidth, enemyhbheight);
			}else if(enemiess.get(i).get(2)==2){
				canbolge = new TextureRegion(dolucan,0,0,dolucan.getWidth() - 50 ,dolucan.getHeight());
				batch.draw(canbolge,enemiess.get(i).get(0),enemiess.get(i).get(1) - enemyhbheight,enemyhbwidth -50,enemyhbheight);
			}else if(enemiess.get(i).get(2)==1){
                canbolge = new TextureRegion(dolucan,0,0,dolucan.getWidth()-100,dolucan.getHeight());
				batch.draw(canbolge,enemiess.get(i).get(0),enemiess.get(i).get(1)-enemyhbheight,enemyhbwidth -100 ,enemyhbheight);
			}
		}
	}

	public void dusmanYokEt(){
		for(int i=0;i<mermiler.size();i++){
			if(mermiler.get(i).x > dragonwidth + 400){
				mermiler.remove(i);
			}else{
				for(int j=0;j<enemiess.size();j++){
					if(mermiler.get(i).x + playerbulletwidth >=
                            enemiess.get(j).get(0)
					  && mermiler.get(i).y + playerbulletheight> enemiess.get(j).get(1)
							&& mermiler.get(i).y<enemiess.get(j).get(1) + enemyheight - 50
					){
						if(enemiess.get(j).get(2)>0) {
							mermiler.remove(i);
							enemiess.get(j).set(2,enemiess.get(j).get(2) - 1);
							if(enemiess.get(j).get(2)==0){
							    patlamaUret(enemiess.get(j));
                                enemiess.remove(j);
                                yokedilendusman++;
                            }
						}
					}
				}
			}
		}
	}

	public void patlamaUret(ArrayList<Integer> bomb){
	    patlama = new ArrayList<Integer>();
	    patlama.add(bomb.get(0));
	    patlama.add(bomb.get(1));
	    patlamalar.add(patlama);
    }

    public void patlamaCiz() {
        for (int j = 0; j < patlamalar.size(); j++) {
            if (patlamasayc < 33) {
                batch.draw(patlamaefekti[patlamasayc], patlamalar.get(j).get(0), patlamalar.get(j).get(1), enemywidth, enemyheight);
                patlamasayc++;
            } else {
            	patlamasayc=0;
                patlamalar.remove(j);
            }
        }
    }
    public void dusmanMermiUret(ArrayList<Integer> ebullet){
		while(dusmanmermisayac==0) {
			dusmanMermi = new ArrayList<Integer>();
			dusmanMermi.add(ebullet.get(0));
			dusmanMermi.add(ebullet.get(1));
			dusmanMermiler.add(dusmanMermi);
			dusmanmermisayac++;
		}
	}

	public void dusmanSaldiri(){
		for(int i=0;i<enemiess.size();i++){
			if(enemiess.get(i).get(0)-dragonwidth<=300 && enemiess.get(i).get(1)>= dragonY && enemiess.get(i).get(1) + enemyheight <=dragonY + dragonheight){
				if(enemiess.get(i).get(0)>=250) {
					dusmanMermiUret(enemiess.get(i));
				}
			}
		}
	}

	public void dusmanMermiCiz(){
			for(int j=0;j<dusmanMermiler.size();j++){
				batch.draw(enemybullet,dusmanMermiler.get(j).get(0) - 50,dusmanMermiler.get(j).get(1) + 20,50 , 50);
			}
	}

	public void dusmanMermiIlerlet(){
		for(int i=0;i<dusmanMermiler.size();i++){
		    if(dusmanMermiler.get(i).get(0)<=200 &&
					dusmanMermiler.get(i).get(1) > dragonY &&
					dusmanMermiler.get(i).get(1) < dragonY + dragonwidth){
		    	dragoncan--;
		        dusmanMermiler.remove(i);
		        dusmanmermisayac=0;
            }else {
                dusmanMermiler.get(i).set(0, dusmanMermiler.get(i).get(0) - 4);
                if(dusmanMermiler.get(i).get(0) + 50 <0){
                    dusmanMermiler.remove(i);
                }
            }
		}
	}

	public void canCiz(){
		switch (dragoncan){
			case 7 : batch.draw(canlar[7],10,Gdx.graphics.getHeight() - 70,300,60);
			break;

			case 6 : batch.draw(canlar[6],10,Gdx.graphics.getHeight() - 70,300,60);
			break;

			case 5 : batch.draw(canlar[5],10,Gdx.graphics.getHeight() - 70,300,60);
			break;

			case 4 : batch.draw(canlar[4],10,Gdx.graphics.getHeight() - 70,300,60);
			break;

			case 3 : batch.draw(canlar[3],10,Gdx.graphics.getHeight() - 70,300,60);
			break;

			case 2 : batch.draw(canlar[2],10,Gdx.graphics.getHeight() - 70,300,60);
			break;

			case 1 : batch.draw(canlar[1],10,Gdx.graphics.getHeight() - 70,300,60);
			break;

			case 0 : batch.draw(canlar[0],10,Gdx.graphics.getHeight() - 70,300,60);
		}

	}

	public void seviyeArttir(){
        if(yokedilendusman>=seviye*5){
        	if(dragoncan>0) {
				oyunbitti = true;
				kazandiniz = true;
			}
        }else if(dragoncan<=0){
        	oyunbitti=true;
        	kaybettiniz = true;
		}
    }

    public void oyunBitti() {
        if (oyunbitti) {
            if (kazandiniz) {
                batch.draw(kazan, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                if (Gdx.input.justTouched()) {
                    if (Gdx.input.getX() > 506 && Gdx.input.getX() < 748 && Gdx.graphics.getHeight() - Gdx.input.getY() > 50 && Gdx.graphics.getHeight() - Gdx.input.getY() < 360) {
                        oyunbitti = false;
                        kazandiniz = false;
                        seviye++;
                        level.putInteger("seviye3",seviye);
                        level.flush();
                        dragoncan = 7;
                        yokedilendusman = 0;
                        homeX = 0;
                        enemiess.removeAll(enemiess);
                        mermiler.removeAll(mermiler);
                        dusmanMermiler.removeAll(dusmanMermiler);
                        patlamalar.removeAll(patlamalar);
                    }
                }
            }

            if (kaybettiniz) {
                batch.draw(kaybet, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                if (Gdx.input.justTouched()) {
                    if (Gdx.input.getX() > 506 && Gdx.input.getX() < 748 && Gdx.graphics.getHeight() - Gdx.input.getY() > 50 && Gdx.graphics.getHeight() - Gdx.input.getY() < 360) {
                        kaybettiniz = false;
                        oyunbitti = false;
                        dragoncan = 7;
                        yokedilendusman = 0;
                        homeX = 0;
                        enemiess.removeAll(enemiess);
                        mermiler.removeAll(mermiler);
                        dusmanMermiler.removeAll(dusmanMermiler);
                        patlamalar.removeAll(patlamalar);
                    }
                }
            }
        }
    }




	@Override
	public void dispose () {
		batch.dispose();
	}
}
