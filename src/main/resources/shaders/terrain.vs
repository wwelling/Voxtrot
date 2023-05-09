#version 330

uniform mat4 model_view_projection;

in vec3 vertex_coordinates;
in vec3 vertex_color;

out vec3 vertexColor;
 
void main() {
	vertexColor = vertex_color;
	gl_Position = model_view_projection * vec4(vertex_coordinates, 1.0);	
}
