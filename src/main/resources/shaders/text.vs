#version 330

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

in vec2 vertex_position;
in vec3 vertex_color;
in vec2 texture_coordinates;

out vec3 vertexColor;
out vec2 textureCoordinates;

void main() {
    vertexColor = vertex_color;
    textureCoordinates = texture_coordinates;
    mat4 mvp = projection * view * model;
    gl_Position = mvp * vec4(vertex_position, 0.0, 1.0);
}