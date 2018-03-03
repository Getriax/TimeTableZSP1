#import <Foundation/Foundation.h>

@interface com_companyofs_planzsp1_JsoupInterfaceIntegrationImpl : NSObject {
}

-(NSString*)getText:(NSString*)param;
-(NSString*)getElementByTag:(NSString*)param param1:(NSString*)param1 param2:(int)param2;
-(NSString*)getElementByClass:(NSString*)param param1:(NSString*)param1 param2:(int)param2;
-(NSString*)getHref:(NSString*)param;
-(NSString*)parseCode:(NSString*)param;
-(BOOL)isSupported;
@end
