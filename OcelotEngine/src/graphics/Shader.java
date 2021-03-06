package graphics;

import math.Matrix4f;
import math.Vector3f;
import utils.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	private boolean enabled = false;
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	public Shader(String vertPath, String fragPath){
		ID = ShaderUtils.load(vertPath, fragPath);
	}
	
	public int getUniform(String name){
		// if cache exsists
		if(locationCache.containsKey(name))
			return locationCache.get(name);
		// create cache
		int result =glGetUniformLocation(ID, name);
		if(result == -1)
			System.err.println("Could not find uniform variable \"" + name + "\"!");
		else
			locationCache.put(name, result);
		return result;
	}
	
	// set one integer
	public void setUniform1i(String name, int value){
		if(!enabled) enable();
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value){
		if(!enabled) enable();
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y){
		if(!enabled) enable();
		glUniform2f(getUniform(name), x, y);
	}
	public void setUniform3f(String name, Vector3f vector){
		if(!enabled) enable();
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix){
		if(!enabled) enable();
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void enable(){
		glUseProgram(ID);
		enabled = true;
	}
	
	public void disable(){
		glUseProgram(0);
		enabled = false;
	}
}
