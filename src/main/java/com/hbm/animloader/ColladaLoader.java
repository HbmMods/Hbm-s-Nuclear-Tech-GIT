package com.hbm.animloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ColladaLoader {
	//Drillgon200: This code is only slightly less terrible than the first time. I hate XML.
	
	/*
	 * My attempt at making a collada loader.
	 * Some things to note: You can't use child of constraints with it with complete accuracy, 
	 * as this will break the linear interpolation and I don't know how to fix it
	 * To get around this, you can put multiple objects with different parents or origins and toggle their visibility.
	 * It's hacky, but it works, at least if you don't need an object affected by multiple bones at the same time.
	 */
	
	//Bob:
	
	/*
	 * I walk to Burger King, then I walk back home from Burger King
	 * walk to Burger King, then I walk back home I walk back
	 * I walk, then I walk back home from Burger King
	 * I walk, then I walk back home from Burger King
	 * then I walk back home from Burger King
	 * I walk, I walk
	 * walk walk, walk walk
	 * I walk, I walk
	 * Burger King
	 */
	
	public static AnimatedModel load(ResourceLocation file) {
		return load(file, false);
	}
	
	public static AnimatedModel load(ResourceLocation file, boolean flipV) {
		IResource res;
		try {
			res = Minecraft.getMinecraft().getResourceManager().getResource(file);
			Document doc;
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(res.getInputStream());
				return parse(doc.getDocumentElement(), flipV);
			} catch(SAXException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			} catch(ParserConfigurationException e) {
				e.printStackTrace();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		MainRegistry.logger.log(Level.ERROR, "FAILED TO LOAD MODEL: " + file);
		return null;
	}
	
	//Model loading section
	
	private static AnimatedModel parse(Element root, boolean flipV){
		//Should get the first bone
		Element scene = getFirstElement((Element)root.getElementsByTagName("library_visual_scenes").item(0));
		AnimatedModel structure = new AnimatedModel(){
			@Override
			protected void renderWithIndex(float inter, int firstIndex, int nextIndex, float diffN, IAnimatedModelCallback c) {
				for(AnimatedModel m : children){
					m.renderWithIndex(inter, firstIndex, nextIndex, diffN, c);
				}
			}
			@Override
			public void render() {
				for(AnimatedModel m : children){
					m.render();
				}
			}
		};
		for(Element node : getChildElements(scene)){
			if(node.getElementsByTagName("instance_geometry").getLength() > 0){
				structure.children.add(parseStructure(node));
			}
		}
		Map<String, Integer> geometry = parseGeometry((Element)root.getElementsByTagName("library_geometries").item(0), flipV);
		addGeometry(structure, geometry);
		setAnimationController(structure, new AnimationController());
		
		return structure;
	}
	
	private static void setAnimationController(AnimatedModel model, AnimationController control){
		model.controller = control;
		for(AnimatedModel m : model.children)
			setAnimationController(m, control);
	}
	
	private static Element getFirstElement(Node root){
		NodeList nodes = root.getChildNodes();
		for(int i = 0; i < nodes.getLength(); i ++){
			Node node = nodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				return (Element)node;
			}
		}
		return null;
	}
	
	private static List<Element> getElementsByName(Element e, String name){
		List<Element> elements = new ArrayList<Element>();
		NodeList n = e.getChildNodes();
		for(int i = 0; i < n.getLength(); i ++){
			Node node = n.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(name)){
				elements.add((Element) node);
			}
		}
		return elements;
	}
	
	private static List<Element> getChildElements(Element e){
		List<Element> elements = new ArrayList<Element>();
		if(e == null)
			return elements;
		NodeList n = e.getChildNodes();
		for(int i = 0; i < n.getLength(); i ++){
			Node node = n.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				elements.add((Element) node);
			}
		}
		return elements;
	}
	
	private static AnimatedModel parseStructure(Element root){
		AnimatedModel model = new AnimatedModel();
		model.name = root.getAttribute("name");
		
		NodeList children = root.getChildNodes();
		for(int i = 0; i < children.getLength(); i ++){
			Node node = children.item(i);
			if(node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			Element ele = (Element) node;
			if("transform".equals(ele.getAttribute("sid"))){
				//Do I even need to flip the matrix here? No idea!
				model.transform = flipMatrix(parseFloatArray(ele.getTextContent()));
				model.hasTransform = true;
			} else if("instance_geometry".equals(ele.getTagName())){
				model.geo_name = ele.getAttribute("url").substring(1);
			} else if(ele.getElementsByTagName("instance_geometry").getLength() > 0){
				AnimatedModel childModel = parseStructure(ele);
				childModel.parent = model;
				model.children.add(childModel);
			}
		}
		return model;
	}
	
	/*private static void addStructureChildren(Element root, AnimatedModel model){
		NodeList children = root.getChildNodes();
		for(int i = 0; i < children.getLength(); i ++){
			Node node = children.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) node;if(getElementsByName(element, "instance_geometry").size() > 0){
					addGeoNamesToModel(element, model);
				} else if(getElementsByName(element, "node").size() > 0 && "JOINT".equals(((Element)getElementsByName(element, "node").get(0)).getAttribute("type"))){
					AnimatedModel m = parseStructure(element);
					model.children.add(m);
					m.parent = model;
				}
			}
		}
	}
	
	private static void addGeoNamesToModel(Element root, AnimatedModel model){
		List<Element> geo_names = getElementsByName(root, "instance_geometry");
		for(Element e : geo_names){
			String name = e.getAttribute("url").substring(1);
			model.geo_names.add(name);
		}
	}*/
	
	
	//Geometry loading section
	
	//Map of geometry name to display list id
	private static Map<String, Integer> parseGeometry(Element root, boolean flipV){
		Map<String, Integer> allGeometry = new HashMap<String, Integer>();
		for(Element e : getElementsByName(root, "geometry")){
			String name = e.getAttribute("id");
			Element mesh = getElementsByName(e, "mesh").get(0);
			
			float[] positions = new float[0];
			float[] normals = new float[0];
			float[] texCoords = new float[0];
			int[] indices = new int[0];
			
			for(Element section : getChildElements(mesh)){
				String id = section.getAttribute("id");
				if(id.endsWith("mesh-positions")){
					positions = parsePositions(section);
				} else if(id.endsWith("mesh-normals")){
					normals = parseNormals(section);
				} else if(id.endsWith("mesh-map-0")){
					texCoords = parseTexCoords(section);
				} else if(section.getNodeName().equals("triangles")){
					indices = ArrayUtils.addAll(indices, parseIndices(section));
				}
			}
			if(positions.length == 0)
				continue;
			
			int displayList = GL11.glGenLists(1);
			GL11.glNewList(displayList, GL11.GL_COMPILE);
			
			Tessellator tess = Tessellator.instance;
			
			tess.startDrawing(GL11.GL_TRIANGLES);
			
			if(indices.length > 0){
				for(int i = 0; i < indices.length; i += 3){
					
					float v = texCoords[indices[i + 2] * 2 + 1];
					if(flipV){
						v = 1 - v;
					}
					
					tess.setNormal(normals[indices[i + 1] * 3], normals[indices[i + 1] * 3 + 1], normals[indices[i + 1] * 3 + 2]);
					tess.setTextureUV(texCoords[indices[i + 2] * 2], v);
					tess.addVertex(positions[indices[i] * 3], positions[indices[i] * 3 + 1], positions[indices[i] * 3 + 2]);
				}
			}
			
			//ORIGINAL:
			/*BufferBuilder buf = Tessellator.getInstance().getBuffer();
			buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_NORMAL);
			if(indices.length > 0){
				for(int i = 0; i < indices.length; i += 3){
					float v = texCoords[indices[i+2]*2+1];
					if(flipV){
						v = 1-v;
					}
					buf.pos(positions[indices[i]*3], positions[indices[i]*3+1], positions[indices[i]*3+2])
					.tex(texCoords[indices[i+2]*2], v)
					.normal(normals[indices[i+1]*3], normals[indices[i+1]*3+1], normals[indices[i+1]*3+2])
					.endVertex();
				}
			} else {
				
			}*/
			
			tess.draw();
			GL11.glEndList();
			
			allGeometry.put(name, displayList);
		}
		return allGeometry;
	}
	
	private static float[] parsePositions(Element root){
		String content = root.getElementsByTagName("float_array").item(0).getTextContent();
		return parseFloatArray(content);
	}
	
	private static float[] parseNormals(Element root){
		String content = root.getElementsByTagName("float_array").item(0).getTextContent();
		return parseFloatArray(content);
	}
	
	private static float[] parseTexCoords(Element root){
		String content = root.getElementsByTagName("float_array").item(0).getTextContent();
		return parseFloatArray(content);
	}
	
	private static int[] parseIndices(Element root){
		String content = root.getElementsByTagName("p").item(0).getTextContent();
		return parseIntegerArray(content);
	}
	
	private static float[] parseFloatArray(String s){
		if(s.isEmpty()){
			return new float[0];
		}
		String[] numbers = s.split(" ");
		float[] arr = new float[numbers.length];
		for(int i = 0; i < numbers.length; i ++){
			arr[i] = Float.parseFloat(numbers[i]);
		}
		return arr;
	}
	private static int[] parseIntegerArray(String s){
		String[] numbers = s.split(" ");
		int[] arr = new int[numbers.length];
		for(int i = 0; i < numbers.length; i ++){
			arr[i] = Integer.parseInt(numbers[i]);
		}
		return arr;
	}
	
	private static void addGeometry(AnimatedModel m, Map<String, Integer> geometry){
		if(!"".equals(m.geo_name) && geometry.containsKey(m.geo_name))
			m.callList = geometry.get(m.geo_name);
		else {
			m.hasGeometry = false;
			m.callList = -1;
		}
		for(AnimatedModel child : m.children){
			addGeometry(child, geometry);
		}
	}
	
	
	
	
	//Animation loading section
	public static Animation loadAnim(int length, ResourceLocation file){
		IResource res;
		try {
			res = Minecraft.getMinecraft().getResourceManager().getResource(file);
			Document doc;
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(res.getInputStream());
				return parseAnim(doc.getDocumentElement(), length);
			} catch(SAXException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			} catch(ParserConfigurationException e) {
				e.printStackTrace();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		MainRegistry.logger.log(Level.ERROR, "FAILED TO LOAD MODEL: " + file);
		return null;
	}
	
	private static Animation parseAnim(Element root, int length){
		Element anim_section = (Element)root.getElementsByTagName("library_animations").item(0);
		Animation anim = new Animation();
		anim.length = length;
		for(Element e : getChildElements(anim_section)){
			if("animation".equals(e.getNodeName())){
				String name = e.getAttribute("name");
				Transform[] t = null;
				List<Element> elements2 = getChildElements(e);
				if(elements2.isEmpty()){
					continue;
				}
				for(Element e2 : elements2){
					if(e2.getAttribute("id").endsWith("transform")){
						t = parseTransforms(e2);
					} else if(e2.getAttribute("id").endsWith("hide_viewport")){
						setViewportHiddenKeyframes(t, e2);
					}
				}
				anim.objectTransforms.put(name, t);
				anim.numKeyFrames = t.length;
			}
		}
		return anim;
	}
	
	private static Transform[] parseTransforms(Element root){
		String output = getOutputLocation(root);
		for(Element e : getChildElements(root)){
			if(e.getAttribute("id").equals(output)){
				return parseTransformsFromText(e.getElementsByTagName("float_array").item(0).getTextContent());
			}
		}
		System.out.println("Failed to parse transforms! This will not work!");
		System.out.println("Node name: " + root.getTagName());
		return null;
	}
	
	private static void setViewportHiddenKeyframes(Transform[] t, Element root){
		String output = getOutputLocation(root);
		for(Element e : getChildElements(root)){
			if(e.getAttribute("id").equals(output)){
				int[] hiddenFrames = parseIntegerArray(e.getElementsByTagName("float_array").item(0).getTextContent());
				for(int i = 0; i < hiddenFrames.length; i ++){
					t[i].hidden = hiddenFrames[i] > 0 ? true : false;
				}
			}
		}
	}
	
	private static String getOutputLocation(Element root){
		Element sampler = (Element) root.getElementsByTagName("sampler").item(0);
		for(Element e : getChildElements(sampler)){
			if("OUTPUT".equals(e.getAttribute("semantic"))){
				return e.getAttribute("source").substring(1);
			}
		}
		return null;
	}
	
	private static Transform[] parseTransformsFromText(String data){
		float[] floats = parseFloatArray(data);
		Transform[] transforms = new Transform[floats.length/16];
		for(int i = 0; i < floats.length/16; i++){
			float[] rawTransform = new float[16];
			for(int j = 0; j < 16; j ++)
				rawTransform[j] = floats[i*16 + j];
			transforms[i] = new Transform(rawTransform);
		}
		return transforms;
	}
	
	private static float[] flipMatrix(float[] f){
		if(f.length != 16){
			System.out.println("Error flipping matrix: array length not 16. This will not work!");
			System.out.println("Matrix: " + f);
		}
		return new float[]{
			f[0], f[4], f[8], f[12],
			f[1], f[5], f[9], f[13],
			f[2], f[6], f[10], f[14],
			f[3], f[7], f[11], f[15]
		};
	}
	
}