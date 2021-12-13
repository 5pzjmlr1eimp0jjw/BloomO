import React from "react";
import ReactDOM from "react-dom";
import ImageUploading from "react-images-uploading";

const ImageUpload = (props)  => {
    
    const maxNumber = 3;
    const onChange = (imageList, addUpdateIndex) => {
      // data for submit
      props.flag(true);
      props.setImages(imageList);
    };
  
    return (
      <div className="App">
        <ImageUploading
          multiple
          value={props.images}
          onChange={onChange}
          maxNumber={maxNumber}
          dataURLKey="data_url"
        >
          {({
            imageList,
            onImageUpload,
            onImageRemoveAll,
            onImageUpdate,
            onImageRemove,
            isDragging,
            dragProps
          }) => (
          
            <div className="upload__image-wrapper">
              <button
                style={isDragging ? { color: "red" } : null}
                onClick={onImageUpload}
                {...dragProps}
              >
                Upload Image (5 Max)
              </button>
              &nbsp;
              <button onClick={onImageRemoveAll}>Remove all images</button>
              {imageList.map((image, index) => (
                <div key={index} className="image-item">
                  <img src={image.data_url} alt="" width="300" />
                  <div className="image-item__btn-wrapper">
                    <button onClick={() => onImageUpdate(index)}>Update</button>
                    <button onClick={() => onImageRemove(index)}>Remove</button>
                    <button onClick={() => props.finalize(props.images)}>Confirm changes</button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </ImageUploading>
      </div>
    );
}
  
export default ImageUpload;