#version 330

uniform sampler2D textureImage;

in vec3 vertexColor;
in vec2 textureCoordinates;

out vec4 fragColor;

void main() {
    fragColor = vec4(vertexColor, texture(textureImage, textureCoordinates).r);
}