#version 150
// GLSL version 1.50
// Fragment shader for toon shading

#define MAX_LIGHTS 5

// Uniform variables, passed in from host program via suitable 
// variants of glUniform*
uniform vec4 lightDirection[MAX_LIGHTS];
uniform int type[MAX_LIGHTS];
uniform mat4 modelview;

// Variables passed in from the vertex shader
in vec3 normal2;

// Output variable, will be written to framebuffer automatically
out vec4 frag_shaded;

void main() {

	vec3 lightDir;
	if (type[3] != 1)
		lightDir = vec3(0,0,1);
	else
		lightDir = lightDirection[3].xyz;
		
	vec4 color;
	float intensity = dot(lightDir,normalize(modelview * vec4(normal2,0)).xyz);
	if (intensity > 0.95)
		color = vec4(1.0,0.5,0.5,1.0);
	else if (intensity > 0.5)
		color = vec4(0.6,0.3,0.3,1.0);
	else if (intensity > 0.25)
		color = vec4(0.4,0.2,0.2,1.0);
	else
		color = vec4(0.2,0.1,0.1,1.0);
	
	frag_shaded = color;
}
