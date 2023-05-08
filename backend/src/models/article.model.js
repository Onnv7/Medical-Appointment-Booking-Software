import mongoose from "mongoose";

const articleSchema = mongoose.Schema({
    title: {
        type: String,
        require: true,
    },
    image: {
        type: String,
        require: true,
    },
    link: {
        type: String,
        require: true,
    }

}, { timestamps: true });


export default mongoose.model("Article", articleSchema);