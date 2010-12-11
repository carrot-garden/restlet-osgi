/*******************************************************************************
 * Copyright (c) 2010.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.restlet.mongo;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.eclipselabs.mongo.IMongoDB;
import org.eclipselabs.restlet.internal.mongo.Activator;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.util.JSON;

/**
 * @author bhunt
 * 
 */
public class MongoResource extends ServerResource
{
	@Get
	public JSONObject getJSON() throws MongoException, UnknownHostException
	{
		DBCollection collection = getCollection();
		DBObject dbObject = collection.findOne(new BasicDBObject("_id", new ObjectId((String) getRequestAttributes().get("id"))));

		if (dbObject != null)
			return new JSONObject(dbObject.toMap());
		else
		{
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return null;
		}
	}

	@Post
	public String add(JsonRepresentation representation) throws JSONException, UnknownHostException
	{
		JSONObject json = representation.getJsonObject();
		DBCollection collection = getCollection();
		DBObject dbObject = (DBObject) JSON.parse(json.toString());
		collection.insert(dbObject);
		return getReference().toString() + dbObject.get("_id").toString();
	}

	@Put
	public void update(JsonRepresentation representation) throws JSONException, UnknownHostException
	{
		JSONObject json = representation.getJsonObject();
		DBCollection collection = getCollection();
		DBObject dbObject = (DBObject) JSON.parse(json.toString());

		if (collection.findAndModify(new BasicDBObject("_id", new ObjectId((String) getRequestAttributes().get("id"))), dbObject) == null)
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
	}

	@Delete
	public void remove() throws UnknownHostException
	{
		DBCollection collection = getCollection();
		collection.remove(new BasicDBObject("_id", new ObjectId((String) getRequestAttributes().get("id"))));
	}

	private DBCollection getCollection() throws UnknownHostException
	{
		IMongoDB mongoDB = Activator.getInstance().getMongoDB();
		Mongo mongo = mongoDB.getMongo(new MongoURI("mongodb://localhost"));
		DB db = mongo.getDB((String) getRequestAttributes().get("database"));
		DBCollection collection = db.getCollection((String) getRequestAttributes().get("collection"));
		return collection;
	}
}
