#version 150
// GLSL version 1.50
// Fragment shader for diffuse shading in combination with a texture map

#define MAX_LIGHTS 5

// Uniform variables passed in from host program
uniform sampler2D plant;
uniform sampler2D gloss;
uniform vec3 kd;
uniform vec3 ka;
uniform vec3 ca;
uniform vec3 ks;
uniform float phong;
uniform vec4 radiance[MAX_LIGHTS];

// Variables passed in from the vertex shader
in float ndotl[MAX_LIGHTS];
in vec4 lightDir[MAX_LIGHTS];
in vec2 frag_texcoord;
in vec4 frag_normal;
in vec4 eye;

// Output variable, will be written to framebuffer automatically
out vec4 frag_shaded;

void main()
{		
	// The built-in GLSL function "texture" performs the texture lookup
	// frag_shaded = ndotl * texture(myTexture, frag_texcoord);
	
	//Texture
	vec4 tex = texture(plant, frag_texcoord);
	vec4 glo = texture(gloss, frag_texcoord);
	
	//Diffuse
	vec4 diffuse[MAX_LIGHTS];
	for (int i = 0; i < MAX_LIGHTS; i++) {
		diffuse[i] = radiance[i] * ndotl[i] * tex;
	}
	vec4 dif = diffuse[0];
	for (int i = 1; i < MAX_LIGHTS; i++) {
		dif = dif + diffuse[i];
	}
	
	float glossfactor = (glo.x + glo.y + glo.z)/3;
	
	//Specular
	vec4 specular[MAX_LIGHTS];
	for (int i = 0; i < MAX_LIGHTS; i++) {
		vec4 ref = normalize(reflect(-lightDir[i], frag_normal));
		specular[i] = radiance[i]  * glossfactor * pow(max(dot(ref,eye),0),phong);
	}
	vec4 spec = specular[0];
	for (int i = 1; i < MAX_LIGHTS; i++) {
		spec = spec + specular[i];
	}
	
	//Ambient
	vec4 ambient = vec4(ka,0) * vec4(ca,0);
	
	frag_shaded = dif + spec + ambient;
}
