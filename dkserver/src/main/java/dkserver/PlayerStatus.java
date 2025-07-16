package dkserver;

import java.io.Serializable;

public class PlayerStatus implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	String nickname;
	long score;
	int vite;
	boolean alive;
	
	public PlayerStatus(String nickname, long score, int vite, boolean alive) {
		
		this.nickname = nickname;
		this.score = score;
		this.vite = vite;
		this.alive = alive;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
		System.out.println("server: " + score);
	}

	public int getVite() {
		return vite;
	}

	public void setVite(int vite) {
		this.vite = vite;
		System.out.println("server: " + vite);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
		System.out.println("server: " + alive);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append("PlayerStatus [nickname=").append(nickname)
				.append(", score=").append(score)
				.append(", vite=").append(vite)
				.append(", alive=").append(alive)
				.append("]");
		return builder.toString();
	}
	
	
	
	
}
