#version 330

uniform mat4 model_view_projection;

in vec3 vertex_coordinates;
in vec2 texture_coordinates;

out vec2 textureCoordinates;
 
void main() {
	textureCoordinates = texture_coordinates;
	gl_Position = model_view_projection * vec4(vertex_coordinates, 1.0);	
}
