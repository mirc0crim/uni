#version 150
// GLSL version 1.50 
// Vertex shader for brick shading

const float SpecularContribution = 0.3;
const float DiffuseContribution  = 1.0 - SpecularContribution;

// Uniform variables, passed in from host program via suitable 
// variants of glUniform*
uniform mat4 projection;
uniform mat4 modelview;

// Input vertex attributes; passed in from host program to shader
// via vertex buffer objects
in vec4 position;
in vec3 normal;

// Output variables for fragment shader
out float LightIntensity;
out vec2 MCposition;

void main()
{		
	// Compute dot product of normal and light direction
	// and pass color to fragment shader
	// Note: here we assume "lightDirection" is specified in camera coordinates,
	// so we transform the normal to camera coordinates, and we don't transform
	// the light direction, i.e., it stays in camera coordinates

	vec3 ecPosition = (modelview * position).xyz;
    vec3 tnorm = normalize(modelview * vec4(normal,0)).xyz;
    vec3 lightVec = normalize(vec3(0,0,1) - ecPosition);
    vec3 reflectVec = reflect(-lightVec, tnorm);
    vec3 viewVec = normalize(-ecPosition);
    float diffuse = max(dot(lightVec, tnorm), 0.0);
    float spec = 0.0;

    if (diffuse > 0.0) {
        spec = max(dot(reflectVec, viewVec), 0.0);
        spec = pow(spec, 16.0);
    }

    LightIntensity = DiffuseContribution * diffuse + SpecularContribution * spec;
    MCposition = position.xy;

	// Transform position, including projection matrix
	// Note: gl_Position is a default output variable containing
	// the transformed vertex position
	gl_Position = projection * modelview * position;
}
