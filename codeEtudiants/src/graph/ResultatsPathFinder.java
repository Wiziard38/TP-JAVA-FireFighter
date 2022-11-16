package graph;

public class ResultatsPathFinder {
	private Path myPath;
	private long myTime;
	
	public ResultatsPathFinder(Path myPath, long myTime) {
		this.myPath = myPath;
		this.myTime = myTime;
	}
	
	public long getTime() {
		return this.myTime;
	}
	
	public Path getPath() {
		return this.myPath;
	}
	
	public void setPath(Path newPath) {
		this.myPath = newPath;
	}
	
	public void setTime(long newTime) {
		this.myTime = newTime;
	}
}
