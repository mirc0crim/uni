#version 150

// Vertex shader for pseudo shading using the normal 

// Uniform variables, set in main program
uniform mat4 projection;
uniform mat4 modelview;

// Input vertex attributes; passed from main program to shader
// via vertex buffer objects
in vec3 normal;
in vec4 position;
in vec4 color;

// Output variable for fragment shader
out vec4 frag_normal;
out vec4 frag_color;

void main()
{	
	// Transform normal to camera space
	// Note: make sure the 4th component of the normal vector is 0!
	frag_normal = modelview * vec4(normal,0);

	// Pass color to fragment shader
	frag_color = color;

	// Transform position, including projection matrix
	// Note: gl_Position is a default output variable containing
	// the transformed vertex position
	gl_Position = projection * modelview * position;
}
